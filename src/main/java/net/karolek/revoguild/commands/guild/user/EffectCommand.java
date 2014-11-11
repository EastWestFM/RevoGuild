package net.karolek.revoguild.commands.guild.user;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.RandomUtil;
import net.karolek.revoguild.utils.TimeUtil;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectCommand extends SubCommand {

    public EffectCommand() {
        super("efekt", "losowanie efektu dla gildii", "/g efekt", "revoguild.effect", "effect", "buff");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        Guild g = GuildManager.getGuild(p);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

        if (!ItemUtil.checkAndRemove(ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_ENLARGE_VIP : Config.COST_ENLARGE_NORMAL, 1), p))
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

        if (!RandomUtil.getChance(Config.EFFECTS_CHANCE))
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_LUCKY_TO_EFFECT);

        int level = RandomUtil.getRandInt(Config.EFFECTS_LEVEL_MIN, Config.EFFECTS_LEVEL_MAX);
        int time = RandomUtil.getRandInt(Config.EFFECTS_TIME_MIN, Config.EFFECTS_TIME_MAX);
        String effect = Config.EFFECTS_TYPES.get(RandomUtil.getRandInt(0, Config.EFFECTS_TYPES.size()));
        PotionEffect potion = new PotionEffect(PotionEffectType.getByName(effect), TimeUtil.SECOND.getTick(time), level);

        for (Player player : g.getOnlineMembers())
            player.addPotionEffect(potion);

        return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_EFFECT, g).replace("{EFFECT}", effect.toLowerCase().replace("_", " ")).replace("{LEVEL}", Integer.toString(level)).replace("{TIME}", Util.secondsToString(time)));
    }
}
