package Color_yr.ALLmusic;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.regex.Pattern;

public class command extends Command {

    public command() {
        super("music");
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new TextComponent("§d[ALLmusic]§c错误，请使用/music help 获取帮助"));
            return;
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2帮助手册"));
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2使用/music play [音乐ID] 来点歌"));
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2使用/music stop 停止播放歌曲"));
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2使用/music now 查看歌曲队列"));
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2使用/music vote 投票切歌"));
            return;
        } else if (args[0].equalsIgnoreCase("play")) {
            if (args.length < 2) {
                sender.sendMessage(new TextComponent("§d[ALLmusic]§c错误，请输入歌曲ID"));
                return;
            } else {
                if (isInteger(args[1]) == true) {
                    if (PlayMusic.playlist.size() == ALLmusic_BC.Maxlist) {
                        sender.sendMessage(new TextComponent("§d[ALLmusic]§c错误，队列已满"));
                        return;
                    }
                    else if(ALLmusic_BC.Banconfig.getBoolean(args[1],false)==true)
                    {
                        sender.sendMessage(new TextComponent("§d[ALLmusic]§c错误，这首歌被禁点了"));
                        return;
                    }
                    PlayMusic.playlist.put(String.valueOf(PlayMusic.All_music), args[1]);
                    PlayMusic.All_music++;
                    ProxyServer.getInstance().broadcast(new TextComponent("§d[ALLmusic]§2" + sender.getName() +
                            "点歌" + args[1]));
                    logs logs = new logs();
                    logs.log_write("玩家：" + sender.getName() + " 点歌：" + args[1]);
                } else
                    sender.sendMessage(new TextComponent("§d[ALLmusic]§c错误，请输入歌曲数字ID"));
                return;
            }
        } else if (args[0].equalsIgnoreCase("stop")) {
            PlayMusic.SendToOnePlayer("[Stop]", sender.getName());
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2已停止" + sender.getName() + "的音乐播放"));
        } else if (args[0].equalsIgnoreCase("now")) {
            if (PlayMusic.playlist.size() == 0) {
                sender.sendMessage(new TextComponent("§d[ALLmusic]§2队列中无歌曲"));
            }
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2队列中有歌曲数：" + PlayMusic.playlist.size()));
            Iterator<String> iterator = PlayMusic.playlist.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                sender.sendMessage(new TextComponent("§d[ALLmusic]§2当前队列" + key + "->" + PlayMusic.playlist.get(key)));
            }
        } else if (args[0].equalsIgnoreCase("vote")) {
            if (PlayMusic.playlist.size() == 0) {
                sender.sendMessage(new TextComponent("§d[ALLmusic]§2队列中无歌曲"));
            } else if (PlayMusic.Vote_time == 0) {
                PlayMusic.Vote_time = 10;
                PlayMusic.Vote.clear();
                PlayMusic.Vote.put(sender.getName(), "true");
                sender.sendMessage(new TextComponent("§d[ALLmusic]§2已发起切歌投票"));
                ProxyServer.getInstance().broadcast(new TextComponent("§d[ALLmusic]§2" + sender.getName() +
                        "发起了切歌投票，10秒后结束，输入/music vote 同意切歌。"));
            } else if (PlayMusic.Vote_time > 0) {
                if (PlayMusic.Vote.get(sender.getName()) != "true") {
                    PlayMusic.Vote.put(sender.getName(), "true");
                    ProxyServer.getInstance().broadcast(new TextComponent("§d[ALLmusic]§2" + sender.getName() + "同意切歌，共有" +
                            PlayMusic.Vote.size() + "名玩家同意切歌。"));
                }
            }
        }else if(args[0].equalsIgnoreCase("reload"))
        {
            ALLmusic_BC.reloadConfig();
            sender.sendMessage(new TextComponent("§d[ALLmusic]§2已重读配置文件"));
        }
    }
}
