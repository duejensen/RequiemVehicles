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
import org.requiem.mods.vehicles.items.Caravels;
import org.requiem.mods.vehicles.util.VehicleFacadeImpl;

import java.util.ArrayList;
import java.util.List;

public class CaravelFactory {

    public static ArrayList<Integer> caravelList = new ArrayList<>();

    public static void addAllCaravels() {
        for (int i = 0; i < Constants.CARAVEL_LIST.length; i++) {
            int id= Caravels.addCaravels(Constants.CARAVEL_LIST[i],Constants.BOAT_NAMES[i]);
            if (id!=0) {
                caravelList.add(id);
            } else {
                Vehicles.debug(Constants.BOAT_NAMES[i] + " boat - cant' be created, id is 0");
            }
        }
    }

    public static void registerCaravelManageHook() {
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
                            for (int id : CaravelFactory.caravelList) {
                                if (id == itemId) {
                                    Vehicles.debug("Adding manage permissions");
                                    original.add(Actions.actionEntrys[668]);
                                }
                            }
                        }

                        if (item.maySeeHistory(performer)) {
                            int itemId = item.getTemplateId();
                            for (int id : CaravelFactory.caravelList) {
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

    public static void caravelVehicleSettings() {
        try {

            CtClass[] input = {
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.items.Item"),
                    HookManager.getInstance().getClassPool().get("com.wurmonline.server.behaviours.Vehicle")
            };
            CtClass output = CtPrimitiveType.voidType;
            HookManager.getInstance().registerHook("com.wurmonline.server.behaviours.Vehicles", "setSettingsForVehicle",
                    Descriptor.ofMethod(output, input), () -> (proxy, method, args) -> {
                        Vehicles.debug("Adding vehicle configuration for caravels");
                        Item item = (Item) args[0];
                        int templateId = item.getTemplateId();
                        for (int i: caravelList) {
                            if (i==templateId) {
                                Vehicle vehicle = (Vehicle) args[1];
                                VehicleFacadeImpl vehfacade = new VehicleFacadeImpl(vehicle);
                                vehfacade.createPassengerSeats(13);
                     
                                 vehfacade.setPilotName("Captain" );
                                 vehfacade.setCreature(false);
                                 vehfacade.setEmbarkString("board");
                                 vehfacade.setEmbarksString("boards");
                                 
                                 vehicle.name = item.getName();
                                 vehicle.setSeatFightMod(0, 0.9f, 0.9f);
                                 vehicle.setSeatOffset(0, 0.0f, 0.0f, 0.0f, 3.866f);
                                 vehicle.setSeatOffset(1, -6.98f, 0.0f, 12.189f);
                                 vehicle.setSeatOffset(2, -14.716f, -0.202f, 3.402f);
                                 vehicle.setSeatOffset(3, -4.417f, 1.024f, 2.013f);
                                 vehicle.setSeatOffset(4, 1.206f, -0.657f, 4.099f);
                                 vehicle.setSeatOffset(5, -7.953f, 0.028f, 0.731f);
                                 vehicle.setSeatOffset(6, -5.317f, -1.134f, 1.941f);
                                 vehicle.setSeatOffset(7, -7.518f, 1.455f, 0.766f);
                                 vehicle.setSeatOffset(8, -2.598f, -0.104f, 2.22f);
                                 vehicle.setSeatOffset(9, -12.46f, 0.796f, 2.861f);
                                 vehicle.setSeatOffset(10, -12.417f, -0.82f, 2.852f);
                                 vehicle.setSeatOffset(11, -4.046f, -0.536f, 2.056f);
                                 vehicle.setSeatOffset(12, -1.089f, 1.004f, 3.65f);
                                 vehicle.setSeatOffset(13, -0.942f, -0.845f, 3.678f);

                                 vehfacade.setWindImpact((byte)70);
                                 vehicle.maxHeight = -2.0f;
                                 vehicle.skillNeeded = 24.0f;
                                 vehfacade.setMaxSpeed(4.0f);
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
