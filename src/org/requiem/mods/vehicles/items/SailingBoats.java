package org.requiem.mods.vehicles.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.combat.Armour;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.requiem.mods.vehicles.Vehicles;

import java.io.IOException;

public class SailingBoats {

    public static int addSailingBoats(String model, String name) {
        Vehicles.debug("Initiating Requiem Sailing Boat " + model);
        try {
            return createItem(model, name);
        } catch (Exception e) {
            Vehicles.debug("Initialization of boats failed: " + e.toString());
        }
        return 0;
    }
//        createItemTemplate(491, 2, "sailing boat", "sailing boats",
// "almost full", "somewhat occupied", "half-full", "emptyish",
// "A small sailing boat that will accommodate five people.",
// new short[] { 60, 108, 1, 31, 21, 51, 56, 52, 44, 92, 117, 47, 48, 54, 157, 160, 249 },
// (short)60,
// (short)41,
// 0,
// 9072000L,
// 60, 60, 210,
// -10,
// MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
// "model.structure.small.boat.sailing.",
// 30.0f,
// 140000,
// (byte)14,
// 10000,
// true,
// -1)
// .setSecondryItem("sail")
// .setDyeAmountGrams(5000);
    private static int createItem(String model, String name) throws IOException {
        Vehicles.debug("id :  org.requiem.boat.sailing." + name);
        ItemTemplateBuilder builder = new ItemTemplateBuilder("org.requiem.boat.sailing." + name);
        builder.size(2);
        builder.name(name + " sailing boat", name + " sailing boats", "Traditional sailboats are monohulls, but multi-hull catamarans and trimarans are gaining popularity. Monohull boats generally rely on ballast for stability and usually are displacement hulls. This stabilizing ballast can, in boats designed for racing, be as much as 50% of the weight of the boat, but is generally around 30%. It creates two problems; one, it gives the monohull tremendous inertia, making it less maneuverable and reducing its acceleration. Secondly, unless it has been built with buoyant foam or air tanks, if a monohull fills with water, it will sink.");
        builder.descriptions("almost full", "somewhat occupied", "half-full", "emptyish");
        builder.itemTypes(new short[]{
                ItemTypes.ITEM_TYPE_FLOATING,
                ItemTypes.ITEM_TYPE_NAMED,
                ItemTypes.ITEM_TYPE_HOLLOW,
                ItemTypes.ITEM_TYPE_NOTAKE,
                ItemTypes.ITEM_TYPE_WOOD,
                ItemTypes.ITEM_TYPE_TURNABLE,
                ItemTypes.ITEM_TYPE_DRAGGABLE,
                ItemTypes.ITEM_TYPE_DECORATION,
                ItemTypes.ITEM_TYPE_REPAIRABLE,
                ItemTypes.ITEM_TYPE_COLORABLE,
                ItemTypes.ITEM_TYPE_VEHICLE,
                ItemTypes.ITEM_TYPE_LOCKABLE,
                ItemTypes.ITEM_TYPE_HASDATA,
                ItemTypes.ITEM_TYPE_NORENAME,
                ItemTypes.ITEM_TYPE_NOT_MISSION,
                ItemTypes.ITEM_TYPE_USES_SPECIFIED_CONTAINER_VOLUME,
                ItemTypes.ITEM_TYPE_NOWORKPARENT,
        });
        builder.imageNumber((short) 60);
        builder.behaviourType(BehaviourList.vehicleBehaviour);
        builder.combatDamage(0);
        builder.decayTime(9072000L);
        builder.dimensions(60, 60, 210);
        builder.primarySkill(-10);
        builder.bodySpaces(MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY);
        builder.modelName(model + ".");
        builder.difficulty(30.0F);
        builder.weightGrams(140000);
        builder.material(ItemMaterials.MATERIAL_WOOD_BIRCH);
        builder.value(10000);
        builder.isTraded(true);
        builder.armourType(Armour.ARMOUR_NONE);
        //builder.setSecondaryItem("sail");
        builder.dyeAmountOverrideGrams((short) 5000);
        ItemTemplate result = builder.build();
     
        return result.getTemplateId();
    }

    public static void createCreationEntry(int templateId) {
        final AdvancedCreationEntry lSailBoat = CreationEntryCreator.createAdvancedEntry(
                SkillList.SHIPBUILDING, ItemList.keelPart, ItemList.keelPart, templateId,
                false, false, 0.0f, true, true, CreationCategories.SHIPS);
        lSailBoat.addRequirement(new CreationRequirement(1, ItemList.keelPart, 1, true));
        lSailBoat.addRequirement(new CreationRequirement(2, ItemList.stern, 1, true));
        lSailBoat.addRequirement(new CreationRequirement(3, ItemList.hullPlank, 50, true));
        lSailBoat.addRequirement(new CreationRequirement(4, ItemList.tenon, 50, true));
        lSailBoat.addRequirement(new CreationRequirement(5, ItemList.pegWood, 50, true));
        lSailBoat.addRequirement(new CreationRequirement(6, ItemList.ropeThick, 2, true));
        lSailBoat.addRequirement(new CreationRequirement(7, ItemList.ropeMooring, 1, true));
        lSailBoat.addRequirement(new CreationRequirement(8, ItemList.tar, 10, true));
        lSailBoat.addRequirement(new CreationRequirement(9, ItemList.oar, 2, true));
        lSailBoat.addRequirement(new CreationRequirement(10, ItemList.seat, 4, true));
        lSailBoat.addRequirement(new CreationRequirement(11, ItemList.rigTriangular, 1, true));
        lSailBoat.addRequirement(new CreationRequirement(12, ItemList.belayingPin, 4, true));
        lSailBoat.setIsEpicBuildMissionTarget(false);
    }

}
