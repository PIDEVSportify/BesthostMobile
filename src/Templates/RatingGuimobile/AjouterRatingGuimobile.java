package Templates.RatingGuimobile;

import Entities.camping;
import Services.Camping_c;
import com.codename1.ui.*;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

import java.io.IOException;
import java.util.function.Consumer;

public class AjouterRatingGuimobile extends Form {
   public AjouterRatingGuimobile(Form back,int id_rating){
       Camping_c c=new Camping_c();
       Form rate = new Form(new BoxLayout(BoxLayout.Y_AXIS));
       rate.setUIID("formthree");
       Button buttonrate=new Button("Rate");
       buttonrate.setUIID("Rate");
       Slider starRank = new Slider();
       starRank=createStarRankSlider();
       starRank.setMaxValue(5);
       final int[] value = {0};
       Slider finalStarRank = starRank;
       buttonrate.addActionListener(l ->{
           value[0] = finalStarRank.getProgress();
           if (value[0]==0)
           {
               if(Dialog.show("Warning", "Zero rating", "Keep it", "Cancel")) {
                   try {
                       c.Updaterate(new camping(id_rating, value[0]));
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   Dialog.show("Success", "I'm really sad to hear that", "Ok", null);
               }
           }
           else {
               try {
                   c.Updaterate(new camping(id_rating, value[0]));
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Dialog.show("Success", "You have successfully added a new Rate.", "Ok", null);
           }
           value[0] =0;
           finalStarRank.setProgress(0);
       });
       rate.add(FlowLayout.encloseCenter(finalStarRank)).add(buttonrate);
       rate.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e ->back.showBack());
       rate.show();
   }

   public  AjouterRatingGuimobile(){}

    private void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);
    }

    public Slider createStarRankSlider() {
        Slider starRank = new Slider();
        starRank.setEditable(true);
        starRank.setMinValue(0);
        starRank.setMaxValue(10);
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte)0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        return starRank;
    }

    @Override
    public void forEach(Consumer<? super Component> action) {
        super.forEach(action);
    }
}
