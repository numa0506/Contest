import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
*@author Group1
*/
public abstract class MapData {
    public static final int NUMBER = 3;
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_ITEM = 2;
    public static final int TYPE_GOAL = 3;
    public static String mapImageFiles[]= new String[6];

    public Image[] mapImages;
    public ImageView[][] mapImageViews;
    public int[][] maps;
    public boolean[][] blackOut;
    public int width;
    public int height;
    public int [][] itemList = new int[NUMBER][2];  //アイテムの座標をx軸y軸に分けて保存する配列
    public static boolean[] itemFlag  = new boolean[NUMBER]; //アイテムの回収の有無を保存する配列
    public static MapData mapDataInst;

    /**
    *デフォルトコンストラクタ
    *@param x 横幅
    *@param y 縦幅
    */
    MapData(int x, int y){
        setMapImageFiles();
        mapImages     = new Image[10];
        mapImageViews = new ImageView[y][x];
        for (int i=0; i<4; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width  = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        putItem();
        putGoal();
        setImageViews();
    }

    /**
    *マップに使う画像をパスで指定し、配列mapImageFiles[]に格納する。
    */
    public void setMapImageFiles(){
        mapImageFiles[0] =   "png/SPACE.png";
        mapImageFiles[1] =   "png/WALL.jpg";
        mapImageFiles[2] =   "png/ITEM.png";
        mapImageFiles[3] =   "png/GOAL.png";
        mapImageFiles[4] =   "png/black.png";
        mapImageFiles[5] =   "png/FIRE.png";
    }

    /**
    *引数で指定した番号のアイテムが取得済みかどうかをtrue/falseで返す。
    *@param i 番号
    *@return 取得済みならtrue、そうでなければfalse
    */
    public static boolean getItemFlag(int i){
        return itemFlag[i];
    }

    /**
    *引数で指定された座標にアイテムがあれば拾う。拾ったあとはただの道になる。
    *@param x x座標
    *@param y y座標
    */
    public void earnItem(int x,int y){
        if(getMap(x,y)!=TYPE_ITEM){
            return ;
        }else{
            setMap(x,y,TYPE_NONE);
            setImageViews();
        }
        for(int i=0;i<NUMBER;i++){
            if(itemList[i][0]==x && itemList[i][1]==y){
                itemFlag[i]=true;
            }
        }
    }

    /**
    *ランダムにアイテムを3つ設置する。
    */
    public void putItem(){
        for(int i=0;i<NUMBER;i++){
            boolean flag =false;
            while(!(flag)){
                int x = (int)(Math.random()*width);
                int y = (int)(Math.random()*height);
                if(getMap(x,y)==TYPE_NONE ){
                    setMap(x,y,TYPE_ITEM);
                    itemList[i][0]=x;
                    itemList[i][1]=y;
                    itemFlag[0] = false;
                    itemFlag[1] = false;
                    itemFlag[2] = false;
                    break;
                }
            }
        }
    }

    /**
    *ゴールの座標を(x, y) = (19, 13)に設定する。
    */
    public void putGoal(){
        setMap(19,13,TYPE_GOAL);
    }

    /**
    *@return マップの縦幅
    */
    public int getHeight(){
        return height;
    }

    /**
    *@return マップの横幅
    */
    public int getWidth(){
        return width;
    }

    /**
    *引数で指定された座標の情報を返す。
    *@param x x座標
    *@param y y座標
    *@return マスのタイプ
    */
    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    /**
    *引数で指定された座標の画像を返す。
    *@param x x座標
    *@param y y座標
    *@return 画像
    */
    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    /**
    *引数で指定された座標に、引数で指定されたタイプのマスを設置する。
    *@param x x座標
    *@param y y座標
    *@param type マスのタイプ
    */
    public void setMap(int x, int y, int type){
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        maps[y][x] = type;
    }

    /**
    *マップいっぱいに画像を設置する。
    */
    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    public abstract void setBlackOut(int x,int y);
    public abstract void fillBlack();

    /**
    *引数で指定されたタイプのマスでマップを埋める。
    *@param type マスのタイプ
    */
    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                maps[y][x] = type;
            }
        }
    }

    /**
    *マップに道を作る。
    *@param x 横幅
    *@param y 縦幅
    */
    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
        int[] tmp;

        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);

            }
        }
    }

    /**
    *マップの状況をコンソールに表示する。
    */
    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                if (getMap(x,y) == MapData.TYPE_WALL){
                    System.out.print("++");
                }else if(getMap(x,y) == MapData.TYPE_ITEM){
                    System.out.print("I ");
                }else if(getMap(x,y) == MapData.TYPE_GOAL){
                    System.out.print("G ");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }

    /**
    *@return 取得したアイテムの数
    */
    public static int itemCount(){
        int count = 0;
        for(int i = 0;i<3;i++){
            if(getItemFlag(i)==true){
                count++;
            }
        }
        return count;
    }

    /**
    *@return mapDataInstへの参照
    */
    public static MapData getInstance(){
        return mapDataInst;
    }
}
