import java.util.*;

// ---------------- INTERFACES ----------------

// PowerGenerable
interface PowerGenerable {
    double generatePower(double inputResource);

    default double transmissionLoss(double generatedKWh, double distanceKm) {
        return generatedKWh * 0.03 * Math.sqrt(distanceKm);
    }

    default double carbonCredit(double renewablePercent, double totalKWh) {
        return (renewablePercent / 100) * totalKWh * 0.82;
    }
}

// RenewableGenerable
interface RenewableGenerable extends PowerGenerable {

    double capacityFactor(double actualOutput, double ratedCapacity);

    default double solarOutput(double panelArea, double irradiance, double efficiency) {
        return panelArea * irradiance * efficiency;
    }

    default double windOutput(double radius, double windSpeed, double efficiency) {
        return 0.5 * 1.225 * Math.PI * radius * radius *
                Math.pow(windSpeed, 3) * efficiency;
    }
}

// StorageCapable
interface StorageCapable extends RenewableGenerable {

    void chargeBattery(double excessKWh);

    default double batteryEfficiency(double stored, double retrieved) {
        return (retrieved / stored) * 100;
    }

    default double optimalDischargeTime(double storedKWh, double peakKW) {
        return storedKWh / peakKW;
    }
}

// GridManageable
interface GridManageable {

    double regulateVoltage(double currentLoad);

    default double gridFrequency(double P, double Q) {
        return P / Math.sqrt(P * P + Q * Q);
    }

    default double peakDemandSurcharge(double peakKW, double avgKW) {
        return (peakKW > 1.3 * avgKW) ? peakKW * 0.1 : 0;
    }
}

// LoadBalanceable
interface LoadBalanceable extends GridManageable {

    void redistributeLoad(double[] zoneLoads);

    default double loadVariance(double[] loads) {
        double mean = 0;
        for (double l : loads) mean += l;
        mean /= loads.length;

        double var = 0;
        for (double l : loads)
            var += Math.pow(l - mean, 2);

        return var / loads.length;
    }

    default double spilloverRisk(double supply, double demand) {
        return demand / supply;
    }
}

// SmartMeteringEnabled
interface SmartMeteringEnabled extends LoadBalanceable {

    void readMeterData(String zoneId);

    default double timeOfUseBilling(double kWh, String slot) {
        if (slot.equalsIgnoreCase("off"))
            return kWh * 4.2;
        else if (slot.equalsIgnoreCase("shoulder"))
            return kWh * 6.8;
        else
            return kWh * 11.5;
    }

    default double demandResponseIncentive(double reducedKWh) {
        return reducedKWh * 3.5;
    }
}

// ---------------- CLASSES ----------------

// Infrastructure
class Infrastructure {
    protected String facilityId, location;
    protected int year;
    protected double operationalCost;

    public Infrastructure() {}

    public Infrastructure(String id, String loc, int y, double cost) {
        facilityId = id;
        location = loc;
        year = y;
        operationalCost = cost;
    }

    public double depreciatedValue(double originalCost, int years) {
        return originalCost * (1 - 0.05 * years);
    }

    public double maintenanceCost(double value) {
        return value * 0.025;
    }
}

// PowerStation
class PowerStation extends Infrastructure implements GridManageable, PowerGenerable {

    protected double installedCapacity;
    protected double currentLoad;

    public PowerStation() {}

    public PowerStation(String id, String loc, int y, double cost,
                        double cap, double load) {
        super(id, loc, y, cost);
        installedCapacity = cap;
        currentLoad = load;
    }

    public double generatePower(double input) {
        return input * 0.35; // thermal efficiency
    }

    public double regulateVoltage(double load) {
        double nominal = 230;
        double droop = 5;
        return (nominal - load) / droop;
    }

    public double efficiencyRating(double actualOutput) {
        return (actualOutput / installedCapacity) * 100;
    }
}

// SmartRenewableGrid
class SmartRenewableGrid extends PowerStation
        implements StorageCapable, SmartMeteringEnabled {

    double batteryStorage = 200000; // kWh
    double storedEnergy = 0;

    public SmartRenewableGrid(String id, String loc, int y, double cost,
                             double cap, double load) {
        super(id, loc, y, cost, cap, load);
    }

    // override transmissionLoss (15% improvement)
    public double transmissionLoss(double gen, double dist) {
        double base = StorageCapable.super.transmissionLoss(gen, dist);
        return base * 0.85;
    }

    public double capacityFactor(double actual, double rated) {
        return actual / rated;
    }

    public void chargeBattery(double excess) {
        if (excess > 0.1 * currentLoad)
            storedEnergy += excess;
    }

    public void redistributeLoad(double[] loads) {
        double total = 0;
        for (double l : loads) total += l;

        double equal = total / loads.length;
        for (int i = 0; i < loads.length; i++)
            loads[i] = equal;
    }

    public void readMeterData(String zoneId) {
        System.out.println("Reading meter for zone: " + zoneId);
    }

    // ---------------- CORE COMPUTATION ----------------
    public void computeSystem() {

        // Solar: 50MW, 8 hrs, 18%
        double solar = 50 * 1000 * 8 * 0.18;

        // Wind: simplified
        double wind = 30 * 1000 * 24 * 0.42;

        double totalGen = solar + wind;

        double loss = transmissionLoss(totalGen, 50);

        double net = totalGen - loss;

        chargeBattery(net - currentLoad);

        double revenue =
                timeOfUseBilling(net * 0.5, "off") +
                timeOfUseBilling(net * 0.3, "shoulder") +
                timeOfUseBilling(net * 0.2, "peak");

        double carbon = carbonCredit(100, totalGen);

        double dep = depreciatedValue(1_000_000, 5);
        double maint = maintenanceCost(dep);

        double profit = revenue - (maint + operationalCost);

        System.out.println("\n--- SMART GRID REPORT ---");
        System.out.println("Solar Generation: " + solar + " kWh");
        System.out.println("Wind Generation: " + wind + " kWh");
        System.out.println("Total Generation: " + totalGen);
        System.out.println("Transmission Loss: " + loss);
        System.out.println("Net Energy: " + net);
        System.out.println("Revenue: " + revenue);
        System.out.println("Carbon Credits: " + carbon);
        System.out.println("Profit: " + profit);
    }
}

// ---------------- MAIN ----------------

public class SmartCityGrid {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Facility ID: ");
        String id = sc.next();

        System.out.print("Enter Location: ");
        String loc = sc.next();

        System.out.print("Enter Year: ");
        int year = sc.nextInt();

        System.out.print("Enter Operational Cost: ");
        double cost = sc.nextDouble();

        System.out.print("Enter Capacity (MW): ");
        double cap = sc.nextDouble();

        System.out.print("Enter Current Load (kW): ");
        double load = sc.nextDouble();

        SmartRenewableGrid grid =
                new SmartRenewableGrid(id, loc, year, cost, cap, load);

        grid.computeSystem();

        sc.close();
    }
}