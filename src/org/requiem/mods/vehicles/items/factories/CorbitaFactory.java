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
import org.requiem.mods.vehicles.items.Corbitas;
import org.requiem.mods.vehicles.util.VehicleFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class CorbitaFactory {

    public static ArrayList<Integer> corbitaList = new ArrayList<>();

    public static void addAllCorbitas() {
        for (int i = 0; i < Constants.CORBITA_LIST.length; i++) {
            int id= Corbitas.addCorbitas(Constants.CORBITA_LIST[i],Constants.BOAT_NAMES[i]);
            if (id!=0) {
                corbitaList.add(id);
            } else {
                Vehicles.debug(Constants.BOAT_NAMES[i] + " boat - cant' be created, id is 0");
            }
        }
    }

    public static void registerCorbitaManageHook() {
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
                            for (int id : CorbitaFactory.corbitaList) {
                                if (id == itemId) {
                                    Vehicles.debug("Adding manage permissions");
                                    original.add(Actions.actionEntrys[668]);
                                }
                            }
                        }
                        if (item.maySeeHistory(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : CorbitaFactory.corbitaList) {
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

    public static void registerCorbitaHook() {
        try {
            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
            };
            CtClass output = CtPrimitiveType.voidType;
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        Vehicles.debug("Adding vehicle configuration for corbitas");
                        Item item = (Item) args[0];
                        int templateId = item.getTemplateId();
                        for (int i: CorbitaFactory.corbitaList) {
                            if (i==templateId) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                //Method passenger = vehicle.getClass().getDeclaredMethod("createPassengerSeats", int.class);
                                //passenger.setAccessible(true);
                                //passenger.invoke(vehicle,0);
                                /*
                                else if (item.getTemplateId() == 541) {
                                vehicle.createPassengerSeats(6);
                                vehicle.pilotName = "captain";
                                vehicle.creature = false;
                                vehicle.embarkString = "board";
                                vehicle.embarksString = "boards";
                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 3.02f);
                                vehicle.setSeatOffset(1, -7.192f, -1.036f, 2.16f);
                                vehicle.setSeatOffset(2, 3.0f, 1.287f, 2.47f);
                                vehicle.setSeatOffset(3, -3.657f, 1.397f, 1.93f);
                                vehicle.setSeatOffset(4, 2.858f, -1.076f, 2.473f);
                                vehicle.setSeatOffset(5, -5.625f, 0.679f, 1.926f);
                                vehicle.setSeatOffset(6, -2.3f, -1.838f, 1.93f);
                                vehicle.setWindImpact((byte)60);
                                vehicle.maxHeight = -2.0f;
                                vehicle.skillNeeded = 21.0f;
                                vehicle.setMaxSpeed(3.8f);
                                vehicle.commandType = 1;
                                vehicle.setMaxAllowedLoadDistance(12);
                                }
                                */
                                //BehaviourAccessor.setPilotName(vehicle,"captain");
                                //vehicle.creature = false;
                                //BehaviourAccessor.setEmbarkString(vehicle,"board");
                                ////vehicle.embarksString = "boards";
                                //vehicle.name = item.getName();
                                //vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                //vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 3.02f);
                                //vehicle.setSeatOffset(1, -7.192f, -1.036f, 2.16f);
                                //vehicle.setSeatOffset(2, 3.0f, 1.287f, 2.47f);
                                //vehicle.setSeatOffset(3, -3.657f, 1.397f, 1.93f);
                                //vehicle.setSeatOffset(4, 2.858f, -1.076f, 2.473f);
                                //vehicle.setSeatOffset(5, -5.625f, 0.679f, 1.926f);
                                //vehicle.setSeatOffset(6, -2.3f, -1.838f, 1.93f);
                                //vehicle.skillNeeded = 21.0F;
                                //vehicle.commandType = 1;
                                //vehicle.setMaxAllowedLoadDistance(12);
                                //BehaviourAccessor.setMaxSpeed(vehicle, 3.8F);
                                //BehaviourAccessor.setMaxHeight(vehicle,-0.5F);
                                ////BehaviourAccessor.setWindImpact(vehicle,60);
                                vehfacade.createPassengerSeats(6);
                                vehfacade.setPilotName( "captain");
                                vehfacade.setCreature(false);
                                vehfacade.setEmbarkString("board");
                                vehfacade.setEmbarksString ("boards");
                                vehicle.name = item.getName();
                                vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 3.02f);
                                vehicle.setSeatOffset(1, -7.192f, -1.036f, 2.16f);
                                vehicle.setSeatOffset(2, 3.0f, 1.287f, 2.47f);
                                vehicle.setSeatOffset(3, -3.657f, 1.397f, 1.93f);
                                vehicle.setSeatOffset(4, 2.858f, -1.076f, 2.473f);
                                vehicle.setSeatOffset(5, -5.625f, 0.679f, 1.926f);
                                vehicle.setSeatOffset(6, -2.3f, -1.838f, 1.93f);
                                vehfacade.setWindImpact((byte)60);
                                vehicle.maxHeight = -2.0f;
                                vehicle.skillNeeded = 21.0f;
                                vehfacade.setMaxSpeed(3.8f);
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
