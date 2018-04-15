package org.requiem.mods.vehicles.items.factories;

import com.wurmonline.server.Features;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
import com.wurmonline.server.behaviours.Seat;
import com.wurmonline.server.behaviours.Vehicle;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;
import javassist.bytecode.Descriptor;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.requiem.mods.vehicles.Constants;
import org.requiem.mods.vehicles.Vehicles;
import org.requiem.mods.vehicles.items.Wagons;
import org.requiem.mods.vehicles.util.SeatsFacadeImpl;
import org.requiem.mods.vehicles.util.VehicleFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class WagonFactory {

    public static ArrayList<Integer> wagonList = new ArrayList<>();

    public static void addAllWagons() {
        for (int i = 0; i < Constants.WAGON_LIST.length; i++) {
            int id= Wagons.addWagon(Constants.WAGON_LIST[i],Constants.WAGON_NAMES[i]);
            if (id!=0) {
                wagonList.add(id);
            } else {
                Vehicles.debug(Constants.WAGON_NAMES[i] + " wagon - cant' be created, id is 0");
            }
        }
    }

    public static void createCreationEntries() {
    	for (int id: wagonList) {
    		Wagons.createCreationEntry(id);
    	}
    }

    public static void registerWagonManageHook() {
        try {
            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.creatures.Creature"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item")
            };
            CtClass output = HookManager.getInstance().getClassPool().get("java.util.List");
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.VehicleBehaviour", "getVehicleBehaviours",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        List<ActionEntry> original = (List<ActionEntry>) method.invoke(proxy, args);
                        Item item = (Item) args[1];
                        Creature performer = (Creature) args[0];
                        if (item.mayManage(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : WagonFactory.wagonList) {
                                if (id == itemId) {
                                    Vehicles.debug("Adding manage permissions");
                                    original.add(Actions.actionEntrys[669]);
                                }
                            }
                        }
                        if (item.maySeeHistory(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : WagonFactory.wagonList) {
                                if (id == itemId) {
                                    original.add(new ActionEntry((short)691, "History of Wagon", "viewing"));
                                }
                            }
                        }
                        return original;
                    });
        }
        catch (Exception e) {
            Vehicles.debug("Permission hook: " + e.toString());
        }
    }

    public static void registerWagonHook() {
        try {
            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
            };
            CtClass output = CtPrimitiveType.voidType;
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        Vehicles.debug("Adding vehicle configuration for wagons");
                        Item item = (Item) args[0];
                        int templateId = item.getTemplateId();
                        for (int i: WagonFactory.wagonList) {
                            if (i==templateId) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                if (Features.Feature.WAGON_PASSENGER.isEnabled()) {
                                	vehfacade.createPassengerSeats(1);
                                }
                                else {
                                	vehfacade.createPassengerSeats(0);
                                }
                                vehfacade.setPilotName("driver");
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("ride");
                                vehfacade.setEmbarksString("rides");
                                
                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.3f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 1.453f);
                                if (Features.Feature.WAGON_PASSENGER.isEnabled()) {
                                    vehicle.setSeatFightMod(1, 1.0f, 0.4f);
                                    vehicle.setSeatOffset(1, 4.05f, 0.0f, 0.84f);
                                }
                                vehicle.maxHeightDiff = 0.04f;
                                vehicle.maxDepth = -0.7f;
                                vehicle.skillNeeded = 21.0f;
                                vehfacade.setMaxSpeed(1.0f);
                                vehicle.commandType = 2;
                                SeatsFacadeImpl seatfacad= new SeatsFacadeImpl();
                         
                                final Seat[] hitches = { seatfacad.CreateSeat((byte)2),seatfacad.CreateSeat((byte)2),seatfacad.CreateSeat((byte)2),seatfacad.CreateSeat((byte)2) };
                             
                                hitches[0].offx = -2.0f;
                                hitches[0].offy = -1.0f;
                                hitches[1].offx = -2.0f;
                                hitches[1].offy = 1.0f;
                                hitches[2].offx = -5.0f;
                                hitches[2].offy = -1.0f;
                                hitches[3].offx = -5.0f;
                                hitches[3].offy = 1.0f;
                                vehicle.addHitchSeats(hitches);
                                vehicle.setMaxAllowedLoadDistance(4);
                                return null;
                            }
                        }
                        return method.invoke(proxy,args);
                    });
        } catch (NotFoundException e) {
            Vehicles.debug("Vehicle hook: " + e.toString());
        }
    }
}
