package coloryr.allmusic.message;

public class ListMusicOBJ {
    private String Head;
    private String Item;
    private String Get;

    public ListMusicOBJ() {
        Head = "§d[AllMusic]§2队列中有歌曲数：&Count&";
        Item = "§2%index%->%MusicName% | %MusicAuthor% | %MusicAl% | %MusicAlia%";
        Get = "§d[AllMusic]§2歌曲列表%ListName%获取成功";
    }

    public boolean check() {
        boolean res = false;
        if (Head == null)
            res = true;
        if (Item == null)
            res = true;
        if (Get == null)
            res = true;

        return res;
    }

    public String getGet() {
        return Get;
    }

    public String getItem() {
        return Item;
    }

    public String getHead() {
        return Head;
    }
}
