package org.requiem.mods.vehicles.items;

import com.wurmonline.server.behaviours.BehaviourList;
import com.wurmonline.server.combat.ArmourTypes;
import com.wurmonline.server.items.*;
import com.wurmonline.server.skills.SkillList;
import com.wurmonline.shared.constants.ItemMaterials;
import org.gotti.wurmunlimited.modsupport.ItemTemplateBuilder;
import org.requiem.mods.vehicles.Vehicles;

import java.io.IOException;

/**
 * Base for all wagons
 */
public class Wagons {

    public static int addWagon(String model, String name) {
        Vehicles.debug("Initiating Requiem Wagon " + model);
        try {
            return createItem(model, name);
        } catch (Exception e) {
            Vehicles.debug("Initialization of wagon failed: " + e.toString());
        }
        return 0;
    }
// ItemTemplateCreator.createItemTemplate(
// 850,
// 3,
// "wagon", "wagons",
// "almost full", "somewhat occupied", "half-full", "emptyish",
// "A fairly large wagon designed to be dragged by four animals.",
// new short[] { 108, 1, 31, 21, 51, 52, 44, 117, 193, 134, 47, 48, 176, 180, 160, 54 },
// (short)60,
// (short)41,
// 0,
// 9072000L,
// 550, 300, 410,
// -10,
// MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
// "model.transports.medium.wagon.",
// 70.0f,
// 240000,
// (byte)14,
// 50000,
// true,
// -1,
// 0)
// .setContainerSize(200, 260, 400);
    private static int createItem(String model, String name) throws IOException {
        Vehicles.debug("id :  org.requiem.wagon." + name);
        ItemTemplateBuilder builder = new ItemTemplateBuilder("org.requiem.wagon." + name);
        builder.size(3);
        builder.name(name + " wagon", name + " wagons", "A fairly large wagon designed to be dragged by four animals.");
        builder.descriptions("almost full", "somewhat occupied", "half-full", "emptyish");
        builder.itemTypes(new short[]{
                ItemTypes.ITEM_TYPE_NAMED,
                ItemTypes.ITEM_TYPE_HOLLOW,
                ItemTypes.ITEM_TYPE_NOTAKE,
                ItemTypes.ITEM_TYPE_WOOD,
                ItemTypes.ITEM_TYPE_TURNABLE,
                ItemTypes.ITEM_TYPE_DECORATION,
                ItemTypes.ITEM_TYPE_REPAIRABLE,
                ItemTypes.ITEM_TYPE_VEHICLE,
                ItemTypes.ITEM_TYPE_CART,
                ItemTypes.ITEM_TYPE_VEHICLE_DRAGGED,
                ItemTypes.ITEM_TYPE_LOCKABLE,
                ItemTypes.ITEM_TYPE_HASDATA,
                ItemTypes.ITEM_TYPE_TRANSPORTABLE,
                ItemTypes.ITEM_TYPE_USES_SPECIFIED_CONTAINER_VOLUME,
                ItemTypes.ITEM_TYPE_NOWORKPARENT,
                ItemTypes.ITEM_TYPE_NORENAME
        });
        builder.imageNumber((short)60);
        builder.behaviourType(BehaviourList.vehicleBehaviour);
        builder.combatDamage(0);
        builder.decayTime(9072000L);
        builder.dimensions(550,300,410);
        builder.primarySkill(-10);
        builder.modelName(model + ".");
        builder.difficulty(70.0F);
        builder.weightGrams(240000);
        builder.material(ItemMaterials.MATERIAL_WOOD_BIRCH);
        builder.value(50000);
        builder.isTraded(true);
        builder.armourType(ArmourTypes.ARMOUR_NONE);
        builder.dyeAmountOverrideGrams((short) 0);
        builder.containerSize(200,260,400);
        ItemTemplate resultTemplate = builder.build();
        Vehicles.debug(name + "; Template ID: " + resultTemplate.getTemplateId() + "; vehicle? " + resultTemplate.isVehicle());
        createCreationEntry(resultTemplate);

        return resultTemplate.getTemplateId();
    }

    private static void createCreationEntry(ItemTemplate newWwagon) {

        AdvancedCreationEntry wagon = CreationEntryCreator.createAdvancedEntry(
                SkillList.CARPENTRY_FINE, ItemList.plank, ItemList.wheelAxleSmall, newWwagon.getTemplateId(),
                false, false, 0.0F, true, true, 0, 40.0D, CreationCategories.CARTS);
        wagon.addRequirement(new CreationRequirement(1, ItemList.wheelAxleSmall, 1, true));
        wagon.addRequirement(new CreationRequirement(2, ItemList.plank, 20, true));
        wagon.addRequirement(new CreationRequirement(3, ItemList.shaft, 4, true));
        wagon.addRequirement(new CreationRequirement(4, ItemList.nailsIronSmall, 10, true));
        wagon.addRequirement(new CreationRequirement(5, ItemList.yoke, 2, true));
        wagon.addRequirement(new CreationRequirement(6, ItemList.sheet, 2, true));
    }

}
