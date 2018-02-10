package com.fanwe.shortvideo.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

/**
 * 在线音乐
 *
 * @author wxy
 */
public class MusicListModel extends BaseActModel {
    public List<MusicItemModel> song_list;
//    public Billboard billboard;

    public static class MusicItemModel {
        public String title;
        public String author;
        public String song_id;
        public String lrclink;

//        String artist_id ;
//        String language;
//        String pic_big;
//        String pic_small;
//        String country;
//        String area;
//        String publishtime;
//        String album_no;
//        String lrclink;
//        String copy_type;
//        String hot;
//        String all_artist_ting_uid;
//        String resource_type;
//        String is_new;
//        String rank_change;
//        String rank;
//        String all_artist_id;
//        String style;
//        String del_status;
//        String relate_status;
//        String toneid;
//        String all_rate;
//        int file_duration;
//        int has_mv_mobile;
//        String versions;
//        String bitrate_fee;
//
//        {
//            "0":"0|0", "1":"0|0"
//        }",
//        String biaoshi":"",
//        String info":"",
//        String has_filmtv":"0",
//        String si_proxycompany;
//        String res_encryption_flag;
//        String song_id;
//        String title;
//        String ting_uid;
//        String author;
//        String album_id;
//        String album_title;
//        int is_first_publish;
//        String havehigh;
//        String charge;
//        String has_mv;
//        String learn;
//        String song_source;
//        String piao_id;
//        String korean_bb_song;
//        String resource_type_ext;
//        String mv_provider;
//        String artist_name;
//        String pic_radio;
//        String pic_s500;
//        String pic_premium;
//        String pic_huge;
//        String album_500_500;
//        String album_800_800;
//        String album_1000_1000;
    }
//    public static class Billboard {
//         public String billboard_type;
//         public String billboard_no;
//         public String update_date;
//         public String billboard_songnum;
//         public int havemore;
//         public String name;
//         public String comment;
//         public String pic_s192;
//         public String pic_s640;
//         public String pic_s444;
//         public String pic_s260;
//         public String pic_s21;
//    }
}

