package cn.xihan.age.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @项目名 : AGE动漫
 * @作者 : MissYang
 * @创建时间 : 2021/2/9 15:11
 * @介绍 :
 */
public class DataBean {

    /**
     * AniPreUP : [{"AID":"20200077","Title":"厨神小当家 第二季","NewTitle":"00:10 第4话","PicSmall":"//sc02.alicdn.com/kf/H72fee9e602d448f5867bed17ff90ebf3e.jpg","Href":"/detail/20200077"},{"AID":"20200147","Title":"里世界郊游","NewTitle":"23:30 第4话","PicSmall":"//sc02.alicdn.com/kf/H65d1deb4bee7431da0cd5d3afb152d5at.jpg","Href":"/detail/20200147"},{"AID":"20210015","Title":"赛马娘 Pretty Derby 第二季","NewTitle":"23:00 第5话","PicSmall":"//sc02.alicdn.com/kf/H13dbe7946db0423a880038885492a090c.jpg","Href":"/detail/20210015"},{"AID":"20200013","Title":"好比是最终迷宫前的少年到新手村生活一般的故事","NewTitle":"22:30 第5话","PicSmall":"//sc02.alicdn.com/kf/Hcbb92175ff7445b081949e440977bf80f.jpg","Href":"/detail/20200013"},{"AID":"20210077","Title":"朋友的妹妹只喜欢烦我","NewTitle":"PV","PicSmall":"https://p.pstatp.com/origin/13803000271e1027303c4","Href":"/detail/20210077"},{"AID":"20210076","Title":"哥布林杀手 第二季","NewTitle":"PV","PicSmall":"https://p.pstatp.com/origin/ff31000350cbb62f0c7f","Href":"/detail/20210076"},{"AID":"20210075","Title":"处刑少女的生存之道","NewTitle":"PV","PicSmall":"https://p.pstatp.com/origin/1386e0001e805ff73f356","Href":"/detail/20210075"},{"AID":"20200361","Title":"埃及神明们的日常","NewTitle":"12:00 第9集","PicSmall":"//sc02.alicdn.com/kf/Hfdbaac190284485b95375904b8f85f542.jpg","Href":"/detail/20200361"},{"AID":"20200144","Title":"女学。～圣女斯克威尔学院～","NewTitle":"第42话","PicSmall":"//sc02.alicdn.com/kf/H1403f7fd73e345b1a53b5305d962073dW.jpg","Href":"/detail/20200144"},{"AID":"20210042","Title":"暗芝居 第八季","NewTitle":"第4话","PicSmall":"//sc02.alicdn.com/kf/H5e3f1a39fea84cb5b209263d8de3efb7p.jpg","Href":"/detail/20210042"},{"AID":"20200045","Title":"HUMAN LOST 人间失格","NewTitle":"[全集]","PicSmall":"//sc02.alicdn.com/kf/H342ebd6d56bd4072b83fcfd7b31c0117L.jpg","Href":"/detail/20200045"},{"AID":"20200004","Title":"进击的巨人 最终季","NewTitle":"第8集","PicSmall":"//sc02.alicdn.com/kf/H3b91b5e70e7a4a8d8e9d40f344b054d8H.jpg","Href":"/detail/20200004"}]
     * AniPreEvDay : [{"AID":"20100008","Title":"学园默示录","NewTitle":"[TV 01-12+OVA]","PicSmall":"//sc02.alicdn.com/kf/H892c9a5c8a5147f4baf0e9cd50eb3d456.jpg","Href":"/detail/20100008"},{"AID":"20190029","Title":"我们真的学不来","NewTitle":"[TV 01-13+OVA]","PicSmall":"//sc02.alicdn.com/kf/H709cf7db3db54adfa1d443daf2e39c73k.jpg","Href":"/detail/20190029"},{"AID":"20090042","Title":"钢壳都市雷吉欧斯","NewTitle":"[TV 01-24]","PicSmall":"//sc02.alicdn.com/kf/Hb87454b57461498abf2f7563271a80d7c.jpg","Href":"/detail/20090042"},{"AID":"20110045","Title":"萝球社！","NewTitle":"[TV 01-12]","PicSmall":"//sc02.alicdn.com/kf/H588bbed37fb64d1e811c99a7433fca75g.jpg","Href":"/detail/20110045"},{"AID":"20180093","Title":"恶魔战线","NewTitle":"[TV 01-12+OAD]","PicSmall":"//sc02.alicdn.com/kf/Hf6fe9070e5d44d1b8b0bf741f9587565t.jpg","Href":"/detail/20180093"},{"AID":"20160138","Title":"无畏魔女","NewTitle":"[TV 01-12]","PicSmall":"//sc02.alicdn.com/kf/H6643f550b48741a5b01625d8229f777dx.jpg","Href":"/detail/20160138"},{"AID":"20160037","Title":"编舟记","NewTitle":"[TV 01-11+SP]","PicSmall":"//sc02.alicdn.com/kf/Hc94fc5454bd34b62bb3aea3a6617fbe59.jpg","Href":"/detail/20160037"},{"AID":"20140079","Title":"漫画家与助手们","NewTitle":"[TV 01-12+OVA]","PicSmall":"//sc02.alicdn.com/kf/H579ccc8de3534a7cabb07a737379f9f7J.jpg","Href":"/detail/20140079"},{"AID":"20070045","Title":"向阳素描","NewTitle":"[BD 01-12]","PicSmall":"//sc02.alicdn.com/kf/He7e9e89ec57d48969c5259d9ac03cbdec.jpg","Href":"/detail/20070045"},{"AID":"20190045","Title":"不吉波普不笑","NewTitle":"[TV 01-18]","PicSmall":"//sc02.alicdn.com/kf/He283078ad2b84662a74deb3b4caf5a3dr.jpg","Href":"/detail/20190045"},{"AID":"20180209","Title":"阿拉涅的虫笼","NewTitle":"[全集]","PicSmall":"//sc02.alicdn.com/kf/H76c2147840794bdf9d8dbf3dc06ce193u.jpg","Href":"/detail/20180209"},{"AID":"20140058","Title":"东京残响","NewTitle":"[TV 01-11]","PicSmall":"//sc02.alicdn.com/kf/H4a0945188fa044458c588685ab2b4cb2O.jpg","Href":"/detail/20140058"}]
     * XinfansInfo : [{"isnew":false,"id":"20200087","wd":1,"name":"ReBIRTH","mtime":"2020-09-22 12:46:08","namefornew":"第24话"},{"isnew":false,"id":"20200121","wd":1,"name":"王者天下 第三季","mtime":"2020-04-27 03:45:45","namefornew":"第4话"},{"isnew":false,"id":"20200027","wd":1,"name":"满溢的水果挞","mtime":"2020-12-28 21:35:13","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200310","wd":1,"name":"黄金神威 第三季","mtime":"2020-12-22 02:54:22","namefornew":"第12话(完结)"},{"isnew":true,"id":"20200012","wd":1,"name":"无职转生 ～在异世界认真地活下去～","mtime":"2021-02-01 00:37:08","namefornew":"00:30 第4话"},{"isnew":true,"id":"20200062","wd":1,"name":"IDOLY PRIDE","mtime":"2021-02-01 00:38:24","namefornew":"00:30 第4话"},{"isnew":true,"id":"20210030","wd":1,"name":"悠哉日常大王 第三季","mtime":"2021-02-01 01:38:53","namefornew":"01:35 第4话"},{"isnew":true,"id":"20200240","wd":1,"name":"EX-ARM","mtime":"2021-02-01 01:46:14","namefornew":"01:40 第4话"},{"isnew":true,"id":"20200004","wd":1,"name":"进击的巨人 最终季","mtime":"2021-02-01 02:02:23","namefornew":"第8集"},{"isnew":true,"id":"20210042","wd":1,"name":"暗芝居 第八季","mtime":"2021-02-01 03:12:50","namefornew":"第4话"},{"isnew":true,"id":"20200144","wd":1,"name":"女学。～圣女斯克威尔学院～","mtime":"2021-02-01 10:26:36","namefornew":"第42话"},{"isnew":true,"id":"20200361","wd":1,"name":"埃及神明们的日常","mtime":"2021-02-01 13:12:56","namefornew":"12:00 第9集"},{"isnew":true,"id":"20200013","wd":1,"name":"好比是最终迷宫前的少年到新手村生活一般的故事","mtime":"2021-02-01 22:26:30","namefornew":"22:30 第5话"},{"isnew":true,"id":"20210015","wd":1,"name":"赛马娘 Pretty Derby 第二季","mtime":"2021-02-01 23:04:26","namefornew":"23:00 第5话"},{"isnew":true,"id":"20200147","wd":1,"name":"里世界郊游","mtime":"2021-02-01 23:33:07","namefornew":"23:30 第4话"},{"isnew":false,"id":"20180346","wd":2,"name":"One Room 第三季","mtime":"2020-12-22 02:59:48","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200332","wd":2,"name":"A3!满开剧团 秋&冬","mtime":"2020-12-29 00:36:08","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200199","wd":2,"name":"在魔王城说晚安","mtime":"2020-12-22 03:05:31","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200243","wd":2,"name":"池袋西口公园","mtime":"2020-12-22 21:02:36","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200210","wd":2,"name":"灾祸的真理 -ZUERST-","mtime":"2020-12-29 23:54:33","namefornew":"第12集(完结)"},{"isnew":true,"id":"20200077","wd":2,"name":"厨神小当家 第二季","mtime":"2021-02-02 00:12:11","namefornew":"00:10 第4话"},{"isnew":false,"id":"20210013","wd":2,"name":"碧蓝航线 微速前进","mtime":"2021-01-26 00:23:14","namefornew":"00:20 第3集"},{"isnew":false,"id":"20200265","wd":2,"name":"阿松 第三季","mtime":"2021-01-26 01:32:37","namefornew":"01:30 第16话"},{"isnew":false,"id":"20200218","wd":2,"name":"WAVE!!","mtime":"2021-01-26 02:05:05","namefornew":"02:00 第3话"},{"isnew":false,"id":"20200079","wd":2,"name":"影之诗","mtime":"2021-01-26 18:03:21","namefornew":"17:55 第39话"},{"isnew":false,"id":"20170082","wd":2,"name":"黑色五叶草","mtime":"2021-01-26 18:46:29","namefornew":"18:25 第161话"},{"isnew":false,"id":"20210032","wd":2,"name":"剧偶像","mtime":"2021-01-26 20:34:16","namefornew":"20:30 第4话"},{"isnew":false,"id":"20210053","wd":2,"name":"奇蛋物语","mtime":"2021-01-26 23:02:14","namefornew":"23:00 第3话"},{"isnew":false,"id":"20200003","wd":2,"name":"关于我转生变成史莱姆这档事 第二季","mtime":"2021-01-26 23:29:22","namefornew":"23:30 第27话"},{"isnew":false,"id":"20200171","wd":3,"name":"熊熊勇闯异世界","mtime":"2020-12-23 21:33:46","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200215","wd":3,"name":"月歌 第二季","mtime":"2020-12-30 21:47:59","namefornew":"第13话(完结)"},{"isnew":false,"id":"20200317","wd":3,"name":"大贵族","mtime":"2020-12-30 22:08:21","namefornew":"第13集(完结)"},{"isnew":false,"id":"20200314","wd":3,"name":"土下座跪求给看","mtime":"2021-01-15 13:52:23","namefornew":"第13集(完结)"},{"isnew":false,"id":"20200201","wd":3,"name":"你与我最后的战场，亦或是世界起始的圣战","mtime":"2020-12-23 23:35:44","namefornew":"第12话(完结)"},{"isnew":false,"id":"20210055","wd":3,"name":"文豪野犬 汪！","mtime":"2021-01-27 00:32:41","namefornew":"00:30 第3话"},{"isnew":false,"id":"20210054","wd":3,"name":"世界魔女 起飞！","mtime":"2021-01-27 00:47:50","namefornew":"00:45 第3话"},{"isnew":false,"id":"20200168","wd":3,"name":"七原罪 第四季(愤怒的审判)","mtime":"2021-01-27 17:38:51","namefornew":"17:35 第3话"},{"isnew":false,"id":"20210016","wd":3,"name":"记录的地平线 第三季","mtime":"2021-01-27 18:27:49","namefornew":"18:25 第3话"},{"isnew":false,"id":"20210046","wd":3,"name":"魔术士欧菲流浪之旅 基姆拉克篇","mtime":"2021-01-27 21:03:43","namefornew":"21:00 第2话"},{"isnew":false,"id":"20190127","wd":3,"name":"Re：从零开始的异世界生活 第二季","mtime":"2021-01-27 22:57:19","namefornew":"23:00 第17话"},{"isnew":false,"id":"20210033","wd":3,"name":"ICHU偶像进行曲","mtime":"2021-01-27 23:51:28","namefornew":"23:30 第4话"},{"isnew":false,"id":"20200059","wd":3,"name":"回复术士的重启人生","mtime":"2021-01-28 05:39:16","namefornew":"第3集"},{"isnew":false,"id":"20200001","wd":4,"name":"强袭魔女 通往柏林之路","mtime":"2020-12-24 01:07:45","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200182","wd":4,"name":"小碧蓝幻想！","mtime":"2020-12-24 21:00:20","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200328","wd":4,"name":"恶玉DRIVE","mtime":"2020-12-24 21:05:40","namefornew":"第12话(完结)"},{"isnew":false,"id":"20210034","wd":4,"name":"装甲娘战机","mtime":"2021-01-28 00:32:54","namefornew":"00:30 第4话"},{"isnew":false,"id":"20200076","wd":4,"name":"苍之骑士团","mtime":"2021-01-28 01:02:11","namefornew":"01:00 第4话"},{"isnew":false,"id":"20210035","wd":4,"name":"BEASTARS 第二季","mtime":"2021-01-28 04:03:45","namefornew":"04:00 第4集"},{"isnew":false,"id":"20200173","wd":4,"name":"SHOW BY ROCK!! STARS!","mtime":"2021-01-28 22:05:01","namefornew":"22:00 第4话"},{"isnew":false,"id":"20200070","wd":4,"name":"新石纪 第二季","mtime":"2021-01-28 22:24:55","namefornew":"22:30 第3话"},{"isnew":false,"id":"20190072","wd":4,"name":"摇曳露营△ 第二季","mtime":"2021-01-28 23:09:35","namefornew":"23:00 第4话"},{"isnew":false,"id":"20200071","wd":5,"name":"安达与岛村","mtime":"2020-12-25 15:29:43","namefornew":"[TV 01-12]"},{"isnew":false,"id":"20200007","wd":5,"name":"突击莉莉 BOUQUET","mtime":"2020-12-25 15:10:45","namefornew":"第12集(完结)"},{"isnew":false,"id":"20200133","wd":5,"name":"魔神英雄传 七魂的龙神丸","mtime":"2020-11-20 20:27:18","namefornew":"第9话(完结)"},{"isnew":false,"id":"20200014","wd":5,"name":"魔女之旅","mtime":"2020-12-18 21:34:46","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200145","wd":5,"name":"我立于百万生命之上","mtime":"2020-12-18 22:14:58","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200206","wd":5,"name":"天地创造设计部","mtime":"2021-01-29 00:01:55","namefornew":"00:00 第4话"},{"isnew":false,"id":"20200118","wd":5,"name":"寒蝉鸣泣之时 业","mtime":"2021-01-29 00:50:18","namefornew":"00:30 第17集"},{"isnew":false,"id":"20200032","wd":5,"name":"五等分的新娘 第二季","mtime":"2021-01-29 01:30:10","namefornew":"01:28 第4话"},{"isnew":false,"id":"20210006","wd":5,"name":"2.43 清阴高中男子排球部","mtime":"2021-01-29 01:50:42","namefornew":"01:30 第4话"},{"isnew":false,"id":"20210036","wd":5,"name":"约定的梦幻岛 第二季","mtime":"2021-01-29 01:57:57","namefornew":"01:55 第4话"},{"isnew":false,"id":"20200346","wd":5,"name":"弱势角色友崎君","mtime":"2021-01-29 20:40:25","namefornew":"20:30 第4话"},{"isnew":false,"id":"20190019","wd":5,"name":"转生成蜘蛛又怎样","mtime":"2021-01-29 21:35:54","namefornew":"21:30 第4话"},{"isnew":false,"id":"20200339","wd":5,"name":"D4DJ First Mix","mtime":"2021-01-29 22:05:04","namefornew":"第13集(完结)"},{"isnew":false,"id":"20200022","wd":6,"name":"弩级战队H×EROS","mtime":"2020-11-17 12:48:28","namefornew":"第OAD1话(完结)"},{"isnew":false,"id":"20190398","wd":6,"name":"镇魂街 第二季","mtime":"2020-01-18 20:09:42","namefornew":"第5话"},{"isnew":false,"id":"20200230","wd":6,"name":"总之就是非常可爱","mtime":"2020-12-19 00:11:03","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200177","wd":6,"name":"催眠麦克风","mtime":"2020-12-26 00:34:50","namefornew":"第13话(完结)"},{"isnew":false,"id":"20200078","wd":6,"name":"炎炎消防队 二之章","mtime":"2020-12-12 01:01:08","namefornew":"第24话(完结)"},{"isnew":false,"id":"20200165","wd":6,"name":"期待在地下城邂逅有错吗 第三季","mtime":"2020-12-19 00:56:16","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200313","wd":6,"name":"铁路浪漫谭","mtime":"2020-12-19 01:10:27","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200166","wd":6,"name":"请问您今天要来点兔子吗？ 第三季","mtime":"2020-12-26 22:06:17","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200162","wd":6,"name":"Love Live! 虹咲学园校园偶像同好会","mtime":"2020-12-26 23:07:26","namefornew":"第13话(完结)"},{"isnew":false,"id":"20210038","wd":6,"name":"大人的防具店 第二季","mtime":"2021-01-30 00:13:24","namefornew":"00:10 第5集"},{"isnew":false,"id":"20200082","wd":6,"name":"BACK ARROW","mtime":"2021-01-30 00:34:55","namefornew":"00:30 第4话"},{"isnew":false,"id":"20200217","wd":6,"name":"王之逆袭 意志的继承者","mtime":"2021-01-30 01:33:22","namefornew":"01:23 第18话"},{"isnew":false,"id":"20200096","wd":6,"name":"咒术回战","mtime":"2021-01-30 01:28:11","namefornew":"01:25 第16话"},{"isnew":false,"id":"20210040","wd":6,"name":"重创的伤口","mtime":"2021-01-30 02:57:16","namefornew":"02:25 第4话"},{"isnew":false,"id":"20200226","wd":6,"name":"只有我进入的隐藏地下城","mtime":"2021-01-30 03:02:25","namefornew":"02:55 第4集"},{"isnew":false,"id":"20200319","wd":6,"name":"勇者斗恶龙 达伊的大冒险","mtime":"2021-01-30 10:08:10","namefornew":"09:30 第17话"},{"isnew":false,"id":"20180062","wd":6,"name":"斗罗大陆","mtime":"2021-01-30 10:07:00","namefornew":"第141话"},{"isnew":false,"id":"20200290","wd":6,"name":"半妖的夜叉姬","mtime":"2021-01-30 19:03:53","namefornew":"18:00 第17话"},{"isnew":false,"id":"20000005","wd":6,"name":"名侦探柯南","mtime":"2021-01-30 19:26:07","namefornew":"19:30 第1053集"},{"isnew":false,"id":"20210019","wd":6,"name":"堀与宫村","mtime":"2021-01-30 23:32:57","namefornew":"23:30 第4话"},{"isnew":false,"id":"20210037","wd":6,"name":"WIXOSS DIVA(A)LIVE","mtime":"2021-01-30 13:10:10","namefornew":"第4集"},{"isnew":false,"id":"20200141","wd":0,"name":"战翼的希格德莉法","mtime":"2020-12-27 00:05:53","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200227","wd":0,"name":"成神之日","mtime":"2020-12-27 00:34:41","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200053","wd":0,"name":"魔法科高校的劣等生 来访者篇","mtime":"2020-12-27 01:03:02","namefornew":"第13话(完结)"},{"isnew":false,"id":"20200282","wd":0,"name":"攀岩！ -Sport Climbing Girls-","mtime":"2020-12-20 02:08:20","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200330","wd":0,"name":"体操武士","mtime":"2020-12-20 02:11:22","namefornew":"第11话(完结)"},{"isnew":false,"id":"20210003","wd":0,"name":"无能的奈奈","mtime":"2020-12-27 22:02:49","namefornew":"[TV 01-13+SP]"},{"isnew":false,"id":"20200164","wd":0,"name":"忧国的莫里亚蒂","mtime":"2020-12-20 23:05:58","namefornew":"第11话(完结)"},{"isnew":false,"id":"20200089","wd":0,"name":"前说！","mtime":"2020-12-27 23:36:56","namefornew":"第12话(完结)"},{"isnew":false,"id":"20200204","wd":0,"name":"被神捡到的男人","mtime":"2020-12-20 23:28:40","namefornew":"第12集(完结)"},{"isnew":false,"id":"20210002","wd":0,"name":"工作细胞 第二季","mtime":"2021-01-31 00:04:33","namefornew":"00:00 第4话"},{"isnew":false,"id":"20210005","wd":0,"name":"工作细胞BLACK","mtime":"2021-01-31 00:33:18","namefornew":"00:30 第6话"},{"isnew":false,"id":"20210020","wd":0,"name":"怪病医拉姆尼","mtime":"2021-01-31 02:04:41","namefornew":"02:00 第4话"},{"isnew":false,"id":"20210049","wd":0,"name":"无限滑板","mtime":"2021-01-31 02:34:41","namefornew":"02:30 第4话"},{"isnew":false,"id":"20200305","wd":0,"name":"假面骑士圣刃","mtime":"2021-01-31 10:02:25","namefornew":"第20集"},{"isnew":false,"id":"20200187","wd":0,"name":"闪亮美妙☆频道 第三季","mtime":"2021-01-31 10:17:50","namefornew":"第136话"},{"isnew":false,"id":"20200116","wd":0,"name":"甜梦猫","mtime":"2021-01-31 10:55:32","namefornew":"第39话"},{"isnew":false,"id":"20200095","wd":0,"name":"数码宝贝大冒险(2020)","mtime":"2021-01-31 12:00:23","namefornew":"第34话"},{"isnew":false,"id":"20200114","wd":0,"name":"Healin Good 光之美少女","mtime":"2021-01-31 11:19:22","namefornew":"第42集"},{"isnew":false,"id":"20000001","wd":0,"name":"海贼王","mtime":"2021-01-31 11:52:03","namefornew":"12:00 第960话"},{"isnew":false,"id":"20200083","wd":0,"name":"境界触发者 第二季","mtime":"2021-01-31 13:17:04","namefornew":"第4集"},{"isnew":false,"id":"20170172","wd":0,"name":"博人传 火影忍者新时代","mtime":"2021-01-31 18:08:55","namefornew":"17:30 第184话"},{"isnew":false,"id":"20180105","wd":0,"name":"智龙迷城 (2018)","mtime":"2021-01-31 19:47:59","namefornew":"第139话"},{"isnew":false,"id":"20200157","wd":0,"name":"花样滑冰Stars","mtime":"2021-01-31 22:24:25","namefornew":"22:00 第6话"},{"isnew":false,"id":"20200163","wd":0,"name":"怪物事变","mtime":"2021-01-31 22:52:37","namefornew":"22:30 第4话"},{"isnew":false,"id":"20210041","wd":0,"name":"八十龟酱观察日记 第三季","mtime":"2021-01-17 22:37:39","namefornew":"第4话"},{"isnew":false,"id":"20200186","wd":0,"name":"游戏王SEVENS","mtime":"2021-01-29 14:55:54","namefornew":"第33话"}]
     * Tip : AGE动漫网 备用地址：<a rel="nofollow" target="_blank" href="http://www.agefans.cc" style="color: #60b8cc;text-decoration: none;">www.agefans.cc</a> 欢迎大家分享给身边朋友！为确保正常观看，请使用谷歌浏览器。
     */

    @SerializedName("Tip")
    private String               Tip;
    @SerializedName("AniPreUP")
    private List<AniPreUPDTO>    AniPreUP;
    @SerializedName("AniPreEvDay")
    private List<AniPreEvDayDTO> AniPreEvDay;
    @SerializedName("XinfansInfo")
    private List<XinfansInfoDTO> XinfansInfo;


    public List<AniPreEvDayDTO> getAniPreEvDay() {
        return AniPreEvDay;
    }

    public List<AniPreUPDTO> getAniPreUP() {
        return AniPreUP;
    }

    public List<XinfansInfoDTO> getXinfansInfo() {
        return XinfansInfo;
    }

    public String getTip() {
        return Tip;
    }

    public void setAniPreEvDay(List<AniPreEvDayDTO> aniPreEvDay) {
        AniPreEvDay = aniPreEvDay;
    }

    public void setAniPreUP(List<AniPreUPDTO> aniPreUP) {
        AniPreUP = aniPreUP;
    }

    public void setTip(String tip) {
        Tip = tip;
    }

    public void setXinfansInfo(List<XinfansInfoDTO> xinfansInfo) {
        XinfansInfo = xinfansInfo;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "Tip='" + Tip + '\'' +
                ", AniPreUP=" + AniPreUP +
                ", AniPreEvDay=" + AniPreEvDay +
                ", XinfansInfo=" + XinfansInfo +
                '}';
    }

    public static class AniPreUPDTO {
        /**
         * AID : 20200077
         * Title : 厨神小当家 第二季
         * NewTitle : 00:10 第4话
         * PicSmall : //sc02.alicdn.com/kf/H72fee9e602d448f5867bed17ff90ebf3e.jpg
         * Href : /detail/20200077
         */

        @SerializedName("AID")
        private String AID;
        @SerializedName("Title")
        private String Title;
        @SerializedName("NewTitle")
        private String NewTitle;
        @SerializedName("PicSmall")
        private String PicSmall;
        @SerializedName("Href")
        private String Href;

        public void setNewTitle(String newTitle) {
            NewTitle = newTitle;
        }

        public void setAID(String AID) {
            this.AID = AID;
        }

        public String getNewTitle() {
            return NewTitle;
        }

        public String getAID() {
            return AID;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getTitle() {
            return Title;
        }

        public String getHref() {
            return Href;
        }

        public String getPicSmall() {
            return PicSmall;
        }

        public void setHref(String href) {
            Href = href;
        }

        public void setPicSmall(String picSmall) {
            PicSmall = picSmall;
        }

        @Override
        public String toString() {
            return "AniPreUPDTO{" +
                    "AID='" + AID + '\'' +
                    ", Title='" + Title + '\'' +
                    ", NewTitle='" + NewTitle + '\'' +
                    ", PicSmall='" + PicSmall + '\'' +
                    ", Href='" + Href + '\'' +
                    '}';
        }
    }


    public static class AniPreEvDayDTO {
        /**
         * AID : 20100008
         * Title : 学园默示录
         * NewTitle : [TV 01-12+OVA]
         * PicSmall : //sc02.alicdn.com/kf/H892c9a5c8a5147f4baf0e9cd50eb3d456.jpg
         * Href : /detail/20100008
         */

        @SerializedName("AID")
        private String AID;
        @SerializedName("Title")
        private String Title;
        @SerializedName("NewTitle")
        private String NewTitle;
        @SerializedName("PicSmall")
        private String PicSmall;
        @SerializedName("Href")
        private String Href;

        public void setPicSmall(String picSmall) {
            PicSmall = picSmall;
        }

        public void setHref(String href) {
            Href = href;
        }

        public String getPicSmall() {
            return PicSmall;
        }

        public String getHref() {
            return Href;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAID() {
            return AID;
        }

        public String getNewTitle() {
            return NewTitle;
        }

        public void setAID(String AID) {
            this.AID = AID;
        }

        public void setNewTitle(String newTitle) {
            NewTitle = newTitle;
        }

        @Override
        public String toString() {
            return "AniPreEvDayDTO{" +
                    "AID='" + AID + '\'' +
                    ", Title='" + Title + '\'' +
                    ", NewTitle='" + NewTitle + '\'' +
                    ", PicSmall='" + PicSmall + '\'' +
                    ", Href='" + Href + '\'' +
                    '}';
        }
    }


    public static class XinfansInfoDTO {
        /**
         * isnew : false
         * id : 20200087
         * wd : 1
         * name : ReBIRTH
         * mtime : 2020-09-22 12:46:08
         * namefornew : 第24话
         */

        @SerializedName("isnew")
        private Boolean isnew;
        @SerializedName("id")
        private String  id;
        @SerializedName("wd")
        private Integer wd;
        @SerializedName("name")
        private String  name;
        @SerializedName("mtime")
        private String  mtime;
        @SerializedName("namefornew")
        private String  namefornew;

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getIsnew() {
            return isnew;
        }

        public Integer getWd() {
            return wd;
        }

        public String getId() {
            return id;
        }

        public String getMtime() {
            return mtime;
        }

        public String getNamefornew() {
            return namefornew;
        }

        public void setIsnew(Boolean isnew) {
            this.isnew = isnew;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public void setNamefornew(String namefornew) {
            this.namefornew = namefornew;
        }

        public void setWd(Integer wd) {
            this.wd = wd;
        }

        @Override
        public String toString() {
            return "XinfansInfoDTO{" +
                    "isnew=" + isnew +
                    ", id='" + id + '\'' +
                    ", wd=" + wd +
                    ", name='" + name + '\'' +
                    ", mtime='" + mtime + '\'' +
                    ", namefornew='" + namefornew + '\'' +
                    '}';
        }
    }
}

