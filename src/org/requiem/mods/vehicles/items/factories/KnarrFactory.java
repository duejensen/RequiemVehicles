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
import org.requiem.mods.vehicles.items.Knarrs;
import org.requiem.mods.vehicles.items.SailingBoats;
import org.requiem.mods.vehicles.util.VehicleFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class KnarrFactory {

    public static ArrayList<Integer> knarrList = new ArrayList<>();

    public static void addAllKnarrs() {
        for (int i = 0; i < Constants.KNARR_LIST.length; i++) {
            int id= Knarrs.addKnarrs(Constants.KNARR_LIST[i],Constants.BOAT_NAMES[i]);
            if (id!=0) {
                knarrList.add(id);
            } else {
                Vehicles.debug(Constants.BOAT_NAMES[i] + " boat - cant' be created, id is 0");
            }
        }
    }

    public static void createCreationEntries() {
    	for (int id: knarrList) {
    		Knarrs.createCreationEntry(id);
    	}
    }
    
    public static void registerKnarrManageHook() {
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
                            for (int id : KnarrFactory.knarrList) {
                                if (id == itemId) {
                                    Vehicles.debug("Adding manage permissions");
                                    original.add(Actions.actionEntrys[668]);
                                }
                            }
                        }
                        if (item.maySeeHistory(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : KnarrFactory.knarrList) {
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

    public static void registerKnarrHook() {
        try {

            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
            };
            CtClass output = CtPrimitiveType.voidType;
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        Vehicles.debug("Adding vehicle configuration for knarrs");
                        Item item = (Item) args[0];
                        int templateId = item.getTemplateId();
                        for (int i: KnarrFactory.knarrList) {
                            if (i==templateId) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                //Method passenger = vehicle.getClass().getDeclaredMethod("createPassengerSeats", int.class);
                                //passenger.setAccessible(true);
                                //passenger.invoke(vehicle,0);
                                //BehaviourAccessor.setPilotName(vehicle,"captain");
                                //vehicle.creature = false;
                                //BehaviourAccessor.setEmbarkString(vehicle,"board");
                                ////vehicle.embarksString = "boards";
                                //vehicle.name = item.getName();
                                //vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                //vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 0.787f);
                                //vehicle.setSeatOffset(1, -7.713f, -0.41f, 0.485f);
                                //vehicle.setSeatOffset(2, -9.722f, 0.455f, 0.417f);
                                //vehicle.setSeatOffset(3, -3.85f, -0.412f, 0.598f);
                                //vehicle.setSeatOffset(4, -11.647f, 0.0f, 0.351f);
                                //vehicle.setSeatOffset(5, -1.916f, -0.211f, 0.651f);
                                //vehicle.setSeatOffset(6, -12.627f, 0.018f, 0.469f);
                                //vehicle.setSeatOffset(7, -5.773f, 0.429f, 0.547f);
                                //vehicle.setSeatOffset(8, -2.882f, 0.388f, 0.626f);
                                //vehicle.setSeatOffset(9, -8.726f, 0.013f, 0.445f);
                                //vehicle.setSeatOffset(10, -10.66f, -0.162f, 0.387f);
                                //vehicle.setSeatOffset(11, -7.708f, 0.454f, 0.479f);
                                //vehicle.setSeatOffset(12, -5.773f, -0.429f, 0.547f);
                                //vehicle.skillNeeded = 23.0F;
                                //vehicle.commandType = 1;
                                //vehicle.setMaxAllowedLoadDistance(8);
                                //BehaviourAccessor.setMaxSpeed(vehicle, 4.1F);
                                //BehaviourAccessor.setMaxHeight(vehicle,-0.5F);
                                //BehaviourAccessor.setWindImpact(vehicle,50);
                                vehfacade.createPassengerSeats(12);
                                vehfacade.setPilotName("captain");
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("board");
                                vehfacade.setEmbarksString ( "boards");
                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 0.787f);
                                vehicle.setSeatOffset(1, -7.713f, -0.41f, 0.485f);
                                vehicle.setSeatOffset(2, -9.722f, 0.455f, 0.417f);
                                vehicle.setSeatOffset(3, -3.85f, -0.412f, 0.598f);
                                vehicle.setSeatOffset(4, -11.647f, 0.0f, 0.351f);
                                vehicle.setSeatOffset(5, -1.916f, -0.211f, 0.651f);
                                vehicle.setSeatOffset(6, -12.627f, 0.018f, 0.469f);
                                vehicle.setSeatOffset(7, -5.773f, 0.429f, 0.547f);
                                vehicle.setSeatOffset(8, -2.882f, 0.388f, 0.626f);
                                vehicle.setSeatOffset(9, -8.726f, 0.013f, 0.445f);
                                vehicle.setSeatOffset(10, -10.66f, -0.162f, 0.387f);
                                vehicle.setSeatOffset(11, -7.708f, 0.454f, 0.479f);
                                vehicle.setSeatOffset(12, -5.773f, -0.429f, 0.547f);
                                vehfacade.setWindImpact((byte)50);
                                vehicle.maxHeight = -0.5f;
                                vehicle.skillNeeded = 23.0f;
                                vehfacade.setMaxSpeed(4.1f);
                                vehicle.commandType = 1;
                                vehicle.setMaxAllowedLoadDistance(8);
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
