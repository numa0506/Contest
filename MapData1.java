import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
*@author Group1
*/
public class MapData1 extends MapData {
    public static final int TYPE_BLACK = 4;
    private boolean[][] blackOut;

    /**
    *親クラスのコンストラクタを通したあと、マップを黒塗りする。
    *@param x 横幅
    *@param y 縦幅
    */
    MapData1(int x,int y){
        super(x,y);
        blackOut = new boolean[y][x];
        mapImages[TYPE_BLACK] = new Image(mapImageFiles[TYPE_BLACK]);
        for(int i=0;i<NUMBER;i++){
            blackOut[itemList[i][1]][itemList[i][0]]=true;
        }
        blackOut[13][19]=true;
        setImageViews();
        MusicPlay.playSp1();
    }

    /**
    *マップに使用する画像のパスを、配列mapImageFiles[]に格納する。
    */
    @Override
    public void setMapImageFiles(){
        mapImageFiles[0] =   "png/space1/SPACE1.jpg";
        mapImageFiles[1] =   "png/space1/WALL1.jpg";
        mapImageFiles[2] =   "png/space1/ITEM1.jpg";
        mapImageFiles[3] =   "png/space1/goal1.jpg";
        mapImageFiles[4] =   "png/black.png";
        mapImageFiles[5] =   "png/FIRE.png";

    }

    /**
    *黒塗り画像を設定する。
    */
    @Override
    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                try{
                    if(!(blackOut[y][x])){
                        mapImageViews[y][x] = new ImageView(mapImages[TYPE_BLACK]);
                        continue;
                    }
                    mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
                }catch(NullPointerException e){
                    continue;
                }
            }
        }
    }

    /**
    *blackOut[y][x]にtrueを格納する。
    *@param x x座標
    *@param y y座標
    */
    @Override
    public void setBlackOut(int x,int y){
        blackOut[y][x]=true;
    }

    @Override
    public void fillBlack(){}


    }
