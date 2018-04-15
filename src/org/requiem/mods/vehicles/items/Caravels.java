package org.requiem.mods.vehicles.items;

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.requiem.mods.vehicles.Vehicles;

import java.io.IOException;

public class Caravels {

    public static int addCaravels(String model, String name) {
        Vehicles.debug("Initiating Kingdom Caravels " + model);
        try {
            return createItem(model, name);
        } catch (Exception e) {
            Vehicles.debug("Initialization of caravels failed: " + e.toString());
        }
        return 0;
    }
// 543,
// "caravel", "caravels",
// "almost full", "somewhat occupied", "half-full", "emptyish",
// "An impressive merchant ship.",
// new short[] { 60, 108, 1, 31, 21, 51, 56, 52, 44, 92, 117, 47, 48, 54, 157, 180, 160, 249 },
// (short)60,
// (short)41,
// 0,
// 9072000L,
// 600, 1200, 2100,
// -10,
// MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
// "model.structure.boat.caravel.",
// 70.0f,
// 2400000,
// (byte)14,
// 10000,
// true
// .setContainerSize(300, 600, 600)
// .setSecondryItem("sail")
// .setDyeAmountGrams(220000);
    private static int createItem(String model, String name) throws IOException {
        Vehicles.debug("id :  org.requiem.boat.caravel." + name);
        ItemTemplateBuilder builder = new ItemTemplateBuilder("org.requiem.boat.caravel." + name);
        builder.name(name + " caravel", name + " caravels", "Until the 15th century, Europeans were limited to coastal navigation using the barge or the balinger, ancient cargo vessels of the Mediterranean Sea with a capacity of around 50 to 200 tons. These boats were fragile, with only one mast with a fixed square sail that could not overcome the navigational difficulties of southward oceanic exploration, as the strong winds, shoals and strong ocean currents easily overwhelmed their abilities.");
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
                ItemTypes.ITEM_TYPE_SUPPORTS_SECONDARY_COLOR
        });
        builder.imageNumber((short) 60);
        builder.behaviourType(BehaviourList.vehicleBehaviour);
        builder.combatDamage(0);
        builder.decayTime(9072000L);
        builder.dimensions(600, 1200, 2100);
        builder.primarySkill(-10);
        builder.bodySpaces(MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY);
        builder.modelName(model + ".");
        builder.difficulty(70.0f);
        builder.weightGrams(2400000);
        builder.material(ItemMaterials.MATERIAL_WOOD_BIRCH);
        builder.value(10000);
        builder.isTraded(true);
        builder.containerSize(300, 600, 600);
        //builder.setSecondaryItem("sail");
        builder.dyeAmountOverrideGrams((short) 220000);
        ItemTemplate result = builder.build();
        createCreationEntry(result);

        return result.getTemplateId();
    }

    private static void createCreationEntry(ItemTemplate newCaravel) {
        final AdvancedCreationEntry lCaravel = CreationEntryCreator.createAdvancedEntry(
                SkillList.SHIPBUILDING, ItemList.keelPart, ItemList.keelPart, newCaravel.getTemplateId(),
                false, false, 0.0f, true, true, CreationCategories.SHIPS);
        lCaravel.addRequirement(new CreationRequirement(1, ItemList.keelPart, 3, true));
        lCaravel.addRequirement(new CreationRequirement(2, ItemList.stern, 1, true));
        lCaravel.addRequirement(new CreationRequirement(3, ItemList.hullPlank, 400, true));
        lCaravel.addRequirement(new CreationRequirement(4, ItemList.tenon, 300, true));
        lCaravel.addRequirement(new CreationRequirement(5, ItemList.pegWood, 600, true));
        lCaravel.addRequirement(new CreationRequirement(6, ItemList.rigTriangular, 1, true));
        lCaravel.addRequirement(new CreationRequirement(7, ItemList.ropeThick, 12, true));
        lCaravel.addRequirement(new CreationRequirement(8, ItemList.ropeMooring, 8, true));
        lCaravel.addRequirement(new CreationRequirement(9, ItemList.rudder, 1, true));
        lCaravel.addRequirement(new CreationRequirement(10, ItemList.deckBoard, 80, true));
        lCaravel.addRequirement(new CreationRequirement(11, ItemList.steeringWheel, 1, true));
        lCaravel.addRequirement(new CreationRequirement(12, ItemList.tar, 150, true));
        lCaravel.addRequirement(new CreationRequirement(13, ItemList.oar, 10, true));
        lCaravel.addRequirement(new CreationRequirement(14, ItemList.belayingPin, 10, true));
        lCaravel.addRequirement(new CreationRequirement(15, ItemList.rigSquareTall, 1, true));
        lCaravel.addRequirement(new CreationRequirement(16, ItemList.rigSquare, 1, true));
        lCaravel.addRequirement(new CreationRequirement(17, ItemList.rigSpinnaker, 1, true));
        lCaravel.setIsEpicBuildMissionTarget(false);
    }
}
