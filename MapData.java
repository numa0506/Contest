import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
  public static final int NUMBER = 3;   //アイテムの数を定数で保存
  public static final int TYPE_NONE   = 0;
  public static final int TYPE_WALL   = 1;
  public static final int TYPE_ITEM = 2;
  public static final int TYPE_GOAL = 3;
  public static final int TYPE_BLACK = 4;
  private static final String mapImageFiles[] = {
    "png/SPACE.png",
    "png/WALL.png",
    "png/ITEM.png",   //　追加
    "png/GOAL.png",
    "png/black.png",//黒塗り画像
  };

  private Image[] mapImages;
  private ImageView[][] mapImageViews;
  private int[][] maps;
  private boolean[][] blackOut;//黒塗り判定用配列
  private int width;
  private int height;
  private int [][] itemList = new int[NUMBER][2];  //アイテムの座標をx軸y軸に分けて保存する配列
  private boolean[] itemFlag  = new boolean[NUMBER]; //アイテムの回収の有無を保存する配列

  MapData(int x, int y){
    mapImages     = new Image[5];
    mapImageViews = new ImageView[y][x];
    for (int i=0; i<5; i++) {
      mapImages[i] = new Image(mapImageFiles[i]);
    }

    width  = x;
    height = y;
    maps = new int[y][x];
    blackOut = new boolean[y][x];

    fillMap(MapData.TYPE_WALL);
    digMap(1, 3);
    putItem();   //追加
    putGoal();
    for(int i=0;i<NUMBER;i++){
      blackOut[itemList[i][1]][itemList[i][0]]=true;
    }
    blackOut[13][19]=true;
    setImageViews();
  }
  public boolean getItemFlag(int i){
    return itemFlag[i];
  }
  public void earnItem(int x,int y){ //アイテムを回収する
    if(getMap(x,y)!=TYPE_ITEM){
      return ;
    }else{
      setMap(x,y,TYPE_NONE); //アイテムマスを空白マスにする
      setImageViews();
    }
    for(int i=0;i<NUMBER;i++){
      if(itemList[i][0]==x && itemList[i][1]==y){
        itemFlag[i]=true;
      }
    }
  }

  public void putItem(){     //アイテムを配置する
    for(int i=0;i<NUMBER;i++){
      boolean flag =false;
      while(flag==false){
        int x = (int)(Math.random()*width);
        int y = (int)(Math.random()*height);
        if(getMap(x,y)==TYPE_NONE ){ //ランダムに求めた座標が空白だったらアイテム設置する
          setMap(x,y,TYPE_ITEM);
          itemList[i][0]=x;
          itemList[i][1]=y;
          break;
        }
      }
    }
  }

  public void putGoal(){      //ゴールの配置
    if(getMap(19,13) ==TYPE_NONE){    //空白マスだったらゴールを配置
      setMap(19,13,TYPE_GOAL);
    }else{
      System.err.println("ゴール予定座標に空白マスがありません。");
      System.exit(1);//プログラムを終了する
    }
  }

  public int getHeight(){
    return height;
  }

  public int getWidth(){
    return width;
  }

  public int getMap(int x, int y) {
    if (x < 0 || width <= x || y < 0 || height <= y) {
      return -1;
    }
    return maps[y][x];
  }

  public ImageView getImageView(int x, int y) {
    return mapImageViews[y][x];
  }

  public void setMap(int x, int y, int type){
    if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
      return;
    }
    maps[y][x] = type;
  }

  public void setImageViews() {
    for (int y=0; y<height; y++) {
      for (int x=0; x<width; x++) {
        if(blackOut[y][x] == false){
          mapImageViews[y][x] = new ImageView(mapImages[TYPE_BLACK]);
          continue;
        }
        mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
      }
    }
  }

  public void setBlackOut(int x,int y){
    blackOut[y][x]=true;
  }

  public void fillMap(int type){
    for (int y=0; y<height; y++){
      for (int x=0; x<width; x++){
        maps[y][x] = type;
      }
    }
  }

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

  public void printMap(){
    for (int y=0; y<height; y++){
      for (int x=0; x<width; x++){
        if (getMap(x,y) == MapData.TYPE_WALL){
          System.out.print("++");
        }else{
          System.out.print("  ");
        }
      }
      System.out.print("\n");
    }
  }
}
