package org.requiem.mods.vehicles;

import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.ItemTemplatesCreatedListener;
import org.gotti.wurmunlimited.modloader.interfaces.ServerStartedListener;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.vehicles.ModVehicleBehaviours;
import org.requiem.mods.vehicles.items.factories.*;

import java.util.Properties;
import java.util.logging.Logger;

public class Vehicles implements WurmServerMod, Configurable, ItemTemplatesCreatedListener, ServerStartedListener {

    private boolean wagons;
    private boolean sailingBoats;
    private boolean cogs;
    private boolean caravels;
    private boolean knarrs;
    private boolean corbitas;
    private static boolean debug;
    private static Logger logger = Logger.getLogger(Vehicles.class.getName());

    @Override
    public void configure(Properties properties) {

        wagons = Boolean.valueOf(properties.getProperty("wagons"));
        sailingBoats = Boolean.valueOf(properties.getProperty("sailingBoats"));
        cogs = Boolean.valueOf(properties.getProperty("cogs"));
        knarrs = Boolean.valueOf(properties.getProperty("knarrs"));
        caravels = Boolean.valueOf(properties.getProperty("caravels"));
        corbitas = Boolean.valueOf(properties.getProperty("corbitas"));
        debug = Boolean.valueOf(properties.getProperty("debug", String.valueOf(true)));

        debug("Wagons: " + wagons);
        debug("Sailing Boats: " + sailingBoats);
        debug("Cogs: " + cogs);
        debug("Caravels: " + caravels);
        debug("Knarrs: " + knarrs);
        debug("Corbitas: " + corbitas);

        debug("Initializing wagon hooks");
        WagonFactory.registerWagonHook();
        WagonFactory.registerWagonManageHook();

        debug("Initializing sailing boat hooks");
        SailingBoatFactory.registerSailingBoatHook();
        SailingBoatFactory.registerSailingBoatManageHook();

        debug("Initializing corbita hooks");
        CorbitaFactory.registerCorbitaHook();
        CorbitaFactory.registerCorbitaManageHook();

        debug("Initializing knarr hooks");
        KnarrFactory.registerKnarrHook();
        KnarrFactory.registerKnarrManageHook();

        debug("Initializing cog hooks");
        CogFactory.registerCogHook();
        CogFactory.registerCogManageHook();

        debug("Initializing caravel hooks");
        CaravelFactory.caravelVehicleSettings();
        CaravelFactory.registerCaravelManageHook();
    }

    @Override
    public void init() {
        ModVehicleBehaviours.init();
    }

    public static void debug(String msg) {
        if (debug) {
            logger.info(msg);
        }
    }

    @Override
    public void onItemTemplatesCreated() {
            WagonFactory.addAllWagons();
            SailingBoatFactory.addAllSailingBoats();
            CogFactory.addAllCogs();
            CaravelFactory.addAllCaravels();
            KnarrFactory.addAllKnarrs();
            CorbitaFactory.addAllCorbitas();
    }

	@Override
	public void onServerStarted() {
	    if (wagons) {
            WagonFactory.createCreationEntries();
        }
        if (sailingBoats) {
            SailingBoatFactory.createCreationEntries();
        }
        if (cogs) {
            CogFactory.createCreationEntries();
        }
        if (caravels) {
            CaravelFactory.createCreationEntries();
        }
        if (knarrs) {
            KnarrFactory.createCreationEntries();
        }
        if (corbitas) {
            CorbitaFactory.createCreationEntries();
        }
		
	}
}
