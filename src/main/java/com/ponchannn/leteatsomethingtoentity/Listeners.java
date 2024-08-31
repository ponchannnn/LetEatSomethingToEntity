package com.ponchannn.leteatsomethingtoentity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!(event.getRightClicked() instanceof LivingEntity targetEntity)) return;

        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() == Material.AIR) return;

        Material material = itemStack.getType();


        if (material == Material.MILK_BUCKET) {
            targetEntity.getActivePotionEffects().forEach(effect ->
                    targetEntity.removePotionEffect(effect.getType()));
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.getInventory().addItem(new ItemStack(Material.BUCKET));
            targetEntity.getWorld().playSound(targetEntity.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
        }
        // ポーションの場合
        else if (isPotion(material)) {
            applyPotionEffect(targetEntity, itemStack);
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
            targetEntity.getWorld().playSound(targetEntity.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1.0f, 1.0f);
        }
        // 食べ物かつ対象がプレイヤーの場合
        else if (isFood(material) && targetEntity instanceof Player targetPlayer && canEat(targetPlayer, material)) {
            applyFoodEffect(player, targetPlayer, itemStack);
            itemStack.setAmount(itemStack.getAmount() - 1);
            targetEntity.getWorld().playSound(targetEntity.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0f, 1.0f);
        }
    }

    private boolean isFood (Material material) {
        return material == Material.APPLE ||
                material == Material.BAKED_POTATO ||
                material == Material.BEEF ||
                material == Material.BEETROOT ||
                material == Material.BEETROOT_SOUP ||
                material == Material.BREAD ||
                material == Material.CARROT ||
                material == Material.CHICKEN ||
                material == Material.CHORUS_FRUIT ||
                material == Material.COD ||
                material == Material.COOKED_BEEF ||
                material == Material.COOKED_CHICKEN ||
                material == Material.COOKED_COD ||
                material == Material.COOKED_MUTTON ||
                material == Material.COOKED_PORKCHOP ||
                material == Material.COOKED_RABBIT ||
                material == Material.COOKED_SALMON ||
                material == Material.COOKIE ||
                material == Material.DRIED_KELP ||
                material == Material.ENCHANTED_GOLDEN_APPLE ||
                material == Material.GLOW_BERRIES ||
                material == Material.GOLDEN_APPLE ||
                material == Material.GOLDEN_CARROT ||
                material == Material.HONEY_BOTTLE ||
                material == Material.MELON_SLICE ||
                material == Material.MUSHROOM_STEW ||
                material == Material.MUTTON ||
                material == Material.POISONOUS_POTATO ||
                material == Material.PORKCHOP ||
                material == Material.POTATO ||
                material == Material.PUFFERFISH ||
                material == Material.PUMPKIN_PIE ||
                material == Material.RABBIT ||
                material == Material.RABBIT_STEW ||
                material == Material.ROTTEN_FLESH ||
                material == Material.SALMON ||
                material == Material.SPIDER_EYE ||
                material == Material.SUSPICIOUS_STEW ||
                material == Material.SWEET_BERRIES ||
                material == Material.TROPICAL_FISH;

    }

    private boolean canEat (Player player, Material material) {
        switch (material) {
            case CHORUS_FRUIT, GOLDEN_APPLE, ENCHANTED_GOLDEN_APPLE, HONEY_BOTTLE, SUSPICIOUS_STEW -> {
                return true;
            }
            default -> {
                return player.getFoodLevel() != 20;
            }
        }
    }

    private  boolean isPotion (Material material) {
        return material == Material.POTION;
    }

    private void applyFoodEffect (Player me, Player targetPlayer, ItemStack itemStack) {
        Material material = itemStack.getType();
        switch (material) {
            case APPLE -> setHunger(targetPlayer, 4, 2.4f);
            case BAKED_POTATO -> setHunger(targetPlayer, 5, 6.0f);
            case BEEF -> setHunger(targetPlayer, 3, 1.8f);
            case BEETROOT -> setHunger(targetPlayer, 1, 1.2f);
            case BEETROOT_SOUP -> returnItemAfterConsumption(me, 6, 7.2f, new ItemStack(Material.BOWL));
            case BREAD -> setHunger(targetPlayer, 5, 6.0f);
            case CARROT -> setHunger(targetPlayer, 3, 3.6f);
            case CHICKEN -> setHunger(targetPlayer, 3, 1.8f);
            case CHORUS_FRUIT -> {
                setHunger(targetPlayer, 4, 2.4f);
                playerTeleport(targetPlayer);
            }
            case COD -> setHunger(targetPlayer, 2, 0.4f);
            case COOKED_BEEF -> setHunger(targetPlayer, 8, 12.8f);
            case COOKED_CHICKEN -> setHunger(targetPlayer, 6, 7.2f);
            case COOKED_COD -> setHunger(targetPlayer, 5, 6.0f);
            case COOKED_MUTTON -> setHunger(targetPlayer, 6, 9.6f);
            case COOKED_PORKCHOP -> setHunger(targetPlayer, 8, 12.8f);
            case COOKED_RABBIT -> setHunger(targetPlayer, 5, 6.0f);
            case COOKED_SALMON -> setHunger(targetPlayer, 6, 9.6f);
            case COOKIE -> setHunger(targetPlayer, 2, 0.4f);
            case DRIED_KELP -> setHunger(targetPlayer, 1, 0.6f);
            case ENCHANTED_GOLDEN_APPLE -> {
                setHunger(targetPlayer, 4, 9.6f);
                // エンチャントされた金リンゴの特殊効果
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 1));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 20 * 5 * 60, 0));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 5 * 60, 0));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 2 * 60, 3));
            }
            case GLOW_BERRIES -> setHunger(targetPlayer, 2, 0.4f);
            case GOLDEN_APPLE -> {
                setHunger(targetPlayer, 4, 9.6f);
                // 金リンゴの特殊効果
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0));
            }
            case GOLDEN_CARROT -> setHunger(targetPlayer, 6, 14.4f);
            case HONEY_BOTTLE -> {
                // はちみつ入り瓶の毒解除効果
                targetPlayer.removePotionEffect(PotionEffectType.POISON);
                returnItemAfterConsumption(me, 6, 1.2f, new ItemStack(Material.GLASS_BOTTLE));
            }
            case MELON_SLICE -> setHunger(targetPlayer, 2, 1.2f);
            case MUSHROOM_STEW -> setHunger(targetPlayer, 6, 7.2f);
            case MUTTON -> setHunger(targetPlayer, 2, 1.2f);
            case POISONOUS_POTATO -> {
                setHunger(targetPlayer, 2, 1.2f);
                // 毒じゃがいもの特殊効果
                if (Math.random() < 0.6) {
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
                }
            }
            case PORKCHOP -> setHunger(targetPlayer, 3, 1.8f);
            case POTATO -> setHunger(targetPlayer, 1, 0.6f);
            case PUFFERFISH -> {
                setHunger(targetPlayer, 1, 0.2f);
                // フグの特殊効果
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 1200, 1));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 2));
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 300, 0));
            }
            case PUMPKIN_PIE -> setHunger(targetPlayer, 8, 4.8f);
            case RABBIT -> setHunger(targetPlayer, 3, 1.8f);
            case RABBIT_STEW -> setHunger(targetPlayer, 10, 12.0f);
            case ROTTEN_FLESH -> {
                setHunger(targetPlayer, 4, 0.8f);
                // 腐った肉の特殊効果
                if (Math.random() < 0.8) {
                    targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 0));
                }
            }
            case SALMON -> setHunger(targetPlayer, 2, 0.4f);
            case SPIDER_EYE -> {
                setHunger(targetPlayer, 2, 3.2f);
                // クモの目の特殊効果
                targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
            }
            case SUSPICIOUS_STEW -> {
                applySuspiciousStewEffect(targetPlayer, itemStack);
                returnItemAfterConsumption(me, 6, 7.2f, new ItemStack(Material.BOWL));
            } // 効果はランダムで異なる
            case SWEET_BERRIES -> setHunger(targetPlayer, 2, 0.4f);
            case TROPICAL_FISH -> setHunger(targetPlayer, 1, 0.2f);
            default -> {
                if (itemStack.getItemMeta().hasFood()) setHunger(targetPlayer, itemStack.getItemMeta().getFood().getNutrition(), itemStack.getItemMeta().getFood().getSaturation()); // デフォルト（対応していない場合）
            }
        }
    }

    private void setHunger(Player player, int foodPoints, float saturation) {
        // 指定した食料値と満腹度を回復させる
        player.setFoodLevel(Math.min(player.getFoodLevel() + foodPoints, 20));
        player.setSaturation(Math.min(saturation, player.getFoodLevel()));
    }
    private void returnItemAfterConsumption(Player player, int foodPoints, float saturation, ItemStack returnItem) {
        setHunger(player, foodPoints, saturation);
        // アイテムの返却
        player.getInventory().addItem(returnItem);
    }

    private void applySuspiciousStewEffect(Player player, ItemStack stew) {
        SuspiciousStewMeta stewMeta =(SuspiciousStewMeta) stew.getItemMeta();
        if (stewMeta.hasCustomEffects()) stewMeta.getCustomEffects().forEach(potionEffect -> potionEffect.apply((LivingEntity) player));
    }

    private void applyPotionEffect(LivingEntity entity, ItemStack potion) {
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        if (potionMeta.hasBasePotionType()) potionMeta.getBasePotionType().getPotionEffects().forEach(potionEffect -> potionEffect.apply(entity));
        if (potionMeta.hasCustomEffects()) potionMeta.getCustomEffects().forEach(potionEffect -> potionEffect.apply(entity));

    }

    private void playerTeleport(Player player) {
        Location location = player.getLocation();

        for (int i = 0 ; i < 16; i++) {
            double offsetX = Math.random() * 8 - 4;
            double offsetY = Math.random() * 8 - 4;
            double offsetZ = Math.random() * 8 - 4;

            Location targetLocation = location.clone().add(offsetX, offsetY, offsetZ);

            Location solidBlockLocation = findSolidBlockBelow(targetLocation, location.getBlockY());

            // 適切なテレポート位置が見つかった場合
            if (solidBlockLocation != null) {
                Location teleportLocation = solidBlockLocation.add(0, 1, 0);
                player.teleport(teleportLocation); // ブロックの上にテレポート
                player.getWorld().playSound(teleportLocation, Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                break; // テレポートが成功したらループ終了
            }
        }
    }

    private Location findSolidBlockBelow(Location location, int originalY) {
        for (int y = location.getBlockY(); y > originalY - 4; y--) {
            Location checkLocation = new Location(location.getWorld(), location.getX(), y, location.getZ());
            Material blockType = checkLocation.getBlock().getType();

            // ブロックが不透明でない場合、次の座標を確認
            if (!blockType.isSolid() || blockType == Material.WATER || blockType == Material.LAVA) continue;

            // 上に2ブロックの空間があるか確認
            Location aboveBlock = checkLocation.clone().add(0, 1, 0);
            Location twoBlocksAbove = checkLocation.clone().add(0, 2, 0);
            Material aboveBlockType = aboveBlock.getBlock().getType();
            Material twoAboveBlockType = twoBlocksAbove.getBlock().getType();

            // ブロックの上が2つの空間ブロック、かつ液体でない場合
            if (isSafeToTeleport(aboveBlockType) && isSafeToTeleport(twoAboveBlockType)) {
                return checkLocation; // テレポート可能な位置を返す
            }
        }
        return null; // 適切なブロックが見つからなかった場合
    }

    // テレポートが安全かどうか確認
    private boolean isSafeToTeleport(Material material) {
        // 空気、カーペット、スイレンの葉、(雪)などを許可
        return material == Material.AIR || material == Material.BLACK_CARPET || material == Material.BLUE_CARPET || material == Material.BROWN_CARPET || material == Material.CYAN_CARPET || material == Material.GRAY_CARPET || material == Material.GREEN_CARPET || material == Material.LIGHT_BLUE_CARPET || material == Material.LIGHT_GRAY_CARPET || material == Material.LIME_CARPET || material == Material.MAGENTA_CARPET || material == Material.MOSS_CARPET || material == Material.ORANGE_CARPET || material == Material.PINK_CARPET || material == Material.PURPLE_CARPET || material == Material.RED_CARPET || material == Material.WHITE_CARPET || material == Material.YELLOW_CARPET || material == Material.LILY_PAD; // 10層以上の雪は排除
    }
}


