package com.ninjagame.ninjagame.domain.weapon;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WeaponType {
    // Samurai Weapons
    KATANA("Samurai"),        // Iconic curved sword of the Samurai.
    WAKIZASHI("Samurai"),     // Shorter sword, often paired with a Katana.
    TACHI("Samurai"),         // Predecessor of the Katana, longer and more curved.
    NODACHI("Samurai"),       // Large greatsword used on the battlefield.
    TANTO("Samurai", "Assassin"),// Small dagger for close combat or stealth attacks.
    YARI("Samurai"),          // Long spear used for thrusting attacks.
    NAGINATA("Samurai"),      // Polearm with a curved blade, effective against cavalry.
    KANABO("Samurai"),        // Heavy iron club, used to break armor.
    KUSARIGAMA("Samurai", "Assassin"), // Sickle with a chain, used for trapping and striking.

    // Samurai Ranged Weapons
    YUMI("Samurai", "Marksman"),    // Large asymmetrical longbow.
    HANKYU("Samurai", "Marksman"),  // Smaller bow used for mounted archery.
    SHURIKEN("Samurai", "Assassin"),   // Throwing stars for distraction or light damage.

    // Assassin Melee Weapons
    NINJATO("Assassin"),        // Straight-bladed sword, shorter than a Katana.
    KUNAI("Assassin"),          // Multi-purpose blade used for stabbing or throwing.
    TEKKO_KAGI("Assassin"),     // Metal claws for slashing or climbing.
    SHUKO("Assassin"),          // Spiked climbing claws, also used in combat.
    KAMA("Assassin"),           // Small farming sickle repurposed as a weapon.
    BO_STAFF("Assassin"),       // Wooden staff used for striking and defense.
    JITTE("Assassin"),          // Metal baton used to disarm opponents.

    // Assassin Ranged Weapons
    SENBON("Assassin"),         // Needle-like throwing weapons, sometimes poisoned.
    FUKIYA("Assassin"),         // Blowgun for firing poisoned darts.
    MAKIBISHI("Assassin"),      // Caltrops used to slow down pursuers.

    // Marksman Bows
    DAIKYU("Marksman"),      // Large longbow with extreme range.
    REFLEX_BOW("Marksman"),  // Compact, powerful bow used by Asian warriors.
    RECURVE_BOW("Marksman"), // Bow with curved limbs for extra force.
    SELF_BOW("Marksman"),    // Simple bow made from a single piece of wood.
    LONGBOW("Marksman"),     // Tall, powerful bow used in warfare.
    SHORTBOW("Marksman"),    // Small, agile bow for quick firing.

    // Marksman Crossbows
    HAND_CROSSBOW("Marksman"),     // Small, one-handed crossbow.
    REPEATING_CROSSBOW("Marksman"),// Semi-automatic crossbow for rapid shots.
    ARBALEST("Marksman");          // Heavy crossbow with extreme power.

    private final String[] classes;

    WeaponType(String... classes) {
        this.classes = classes;
    }

    public String[] getClasses() {
        return classes;
    }

    public static List<WeaponType> convertAllowedWeaponTypes(String weaponsString) {
        if (weaponsString == null || weaponsString.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.stream(weaponsString.split("\\s*,\\s*"))
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(WeaponType::isValidWeapon)
                .map(WeaponType::valueOf)
                .collect(Collectors.toList());
    }


    public static WeaponType convertAllowedWeaponType(String weaponString) {
        if (weaponString == null || weaponString.trim().isEmpty()) {
            return null;
        }
        String normalized = weaponString.trim().toUpperCase();
        return isValidWeapon(normalized) ? WeaponType.valueOf(normalized) : null;
    }



    public static String convertListToString(List<WeaponType> weapons) {
        return weapons.stream().map(WeaponType::name).collect(Collectors.joining(","));
    }

    public static String convertToString(WeaponType weapon) {
        return (weapon != null) ? weapon.name() : "";
    }


    public static boolean isValidWeapon(String weaponName) {
        String trimmed = weaponName.trim().toUpperCase();
        return Arrays.stream(WeaponType.values())
                .map(Enum::name)
                .anyMatch(valid -> valid.equalsIgnoreCase(trimmed));
    }


    public String toPrettyString() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace("_", " ");
    }
}
