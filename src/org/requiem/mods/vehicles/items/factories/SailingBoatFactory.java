package org.requiem.mods.vehicles.items.factories;

import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.behaviours.Actions;
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
import org.requiem.mods.vehicles.items.SailingBoats;
import org.requiem.mods.vehicles.items.Wagons;
import org.requiem.mods.vehicles.util.VehicleFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class SailingBoatFactory {

    public static ArrayList<Integer> sailingBoatList = new ArrayList<>();

    public static void addAllSailingBoats() {
        for (int i = 0; i < Constants.SAILING_BOAT_LIST.length; i++) {
            int id= SailingBoats.addSailingBoats(Constants.SAILING_BOAT_LIST[i],Constants.SAILING_BOAT_NAMES[i]);
            if (id!=0) {
                sailingBoatList.add(id);
            } else {
                Vehicles.debug(Constants.SAILING_BOAT_NAMES[i] + " boat - cant' be created, id is 0");
            }
        }
    }

    public static void createCreationEntries() {
    	for (int id: sailingBoatList) {
    		SailingBoats.createCreationEntry(id);
    	}
    }

    public static void registerSailingBoatManageHook() {
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
                            for (int id : SailingBoatFactory.sailingBoatList) {
                                if (id == itemId) {
                                    Vehicles.debug("Adding manage permissions");
                                    original.add(Actions.actionEntrys[668]);
                                }
                            }
                        }
                        if (item.maySeeHistory(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : SailingBoatFactory.sailingBoatList) {
                                if (id == itemId) {
                                    original.add(new ActionEntry((short)691, "History of Ship", "viewing"));
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

    public static void registerSailingBoatHook() {
        try {

            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
            };
            CtClass output = CtPrimitiveType.voidType;
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        Vehicles.debug("Adding vehicle configuration for sailing boats");
                        Item item = (Item) args[0];
                        int templateId = item.getTemplateId();
                        for (int i: SailingBoatFactory.sailingBoatList) {
                            if (i==templateId) {
                                Vehicle vehicle = (Vehicle) args[1];
                                
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                //Method passenger = vehicle.getClass().getDeclaredMethod("createPassengerSeats", int.class);
                                //passenger.setAccessible(true);
                                //passenger.invoke(vehicle, 4);
                                //BehaviourAccessor.setPilotName(vehicle,"captain");
                                //vehicle.creature = false;
                                //BehaviourAccessor.setEmbarkString(vehicle,"board");
                                ////vehicle.embarksString = "boards";
                                //vehicle.creature = false;
                                //vehicle.name = item.getName();
                                //vehicle.setSeatFightMod(0, 0.9f, 0.4f);
                                //vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 0.351f);
                                //vehicle.setSeatOffset(1, -1.392f, 0.378f, 0.351f);
                                //vehicle.setSeatOffset(2, -2.15f, -0.349f, 0.341f);
                                //vehicle.setSeatOffset(3, -3.7f, -0.281f, 0.34f);
                                //vehicle.setSeatOffset(4, -4.39f, 0.14f, 0.352f);
                                //vehicle.skillNeeded = 20.1F;
                                //vehicle.commandType = 1;
                                //vehicle.setMaxAllowedLoadDistance(6);
                                //vehicle.setMaxSpeed( 5.0F);
                                //vehicle.setMaxHeight(-0.5F);
                                //vehicle.setWindImpact((byte) 30);
                                vehfacade.createPassengerSeats(4);
                                vehfacade.setPilotName ( "captain");
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("board");
                                vehfacade.setEmbarksString("boards");

                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.4f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 0.351f);
                                vehicle.setSeatOffset(1, -1.392f, 0.378f, 0.351f);
                                vehicle.setSeatOffset(2, -2.15f, -0.349f, 0.341f);
                                vehicle.setSeatOffset(3, -3.7f, -0.281f, 0.34f);
                                vehicle.setSeatOffset(4, -4.39f, 0.14f, 0.352f);
                                vehfacade.setWindImpact((byte)30);
                                vehicle.maxHeight = -0.5f;
                                vehicle.skillNeeded = 20.1f;
                                vehfacade.setMaxSpeed(5.0f);
                                vehicle.commandType = 1;
                                vehicle.setMaxAllowedLoadDistance(6);
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
