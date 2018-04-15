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
import org.requiem.mods.vehicles.items.Cogs;
import org.requiem.mods.vehicles.util.VehicleFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class CogFactory {

    public static ArrayList<Integer> cogList = new ArrayList<>();

    public static void addAllCogs() {
        for (int i = 0; i < Constants.COG_LIST.length; i++) {
            int id= Cogs.addCogs(Constants.COG_LIST[i],Constants.BOAT_NAMES[i]);
            if (id!=0) {
                cogList.add(id);
            } else {
                Vehicles.debug(Constants.BOAT_NAMES[i] + " boat - cant' be created, id is 0");
            }
        }
    }

    public static void registerCogManageHook() {
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
                            for (int id : cogList) {
                                if (id == itemId) {
                                    Vehicles.debug("Adding manage permissions");
                                    original.add(Actions.actionEntrys[668]);
                                }
                            }
                        }
                        if (item.maySeeHistory(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : cogList) {
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

    public static void registerCogHook() {
        try {
            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
            };
            CtClass output = CtPrimitiveType.voidType;
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        Vehicles.debug("Adding vehicle configuration for cogs");
                        Item item = (Item) args[0];
                        int templateId = item.getTemplateId();
                        for (int i: cogList) {
                            if (i==templateId) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                vehfacade.createPassengerSeats(8);
                                vehfacade.setPilotName("captain");
                              
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("board");
                                vehfacade.setEmbarksString("boards");
                                

                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 4.011f);
                                vehicle.setSeatOffset(1, -16.042f, -0.901f, 3.96f);
                                vehicle.setSeatOffset(2, -7.629f, 0.0f, 14.591f);
                                vehicle.setSeatOffset(3, -4.411f, -2.097f, 3.51f);
                                vehicle.setSeatOffset(4, -16.01f, 0.838f, 3.96f);
                                vehicle.setSeatOffset(5, -9.588f, -1.855f, 1.802f);
                                vehicle.setSeatOffset(6, -11.08f, 2.451f, 1.805f);
                                vehicle.setSeatOffset(7, -4.411f, 1.774f, 3.52f);
                                vehicle.setSeatOffset(8, -1.813f, -1.872f, 3.789f);
                                vehfacade.setWindImpact((byte)80);
                                vehicle.maxHeight = -2.0f;
                                vehicle.skillNeeded = 22.0f;
                                vehfacade.setMaxSpeed(3.5f);
                                vehicle.commandType = 1;
                                vehicle.setMaxAllowedLoadDistance(12);
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
