#L-Weight
  第三方向下支持的仿制Android5.0的控件
  1.波纹效果的Button

##使用方法
  1.将library导入，修改你的工程的gradle如下:
  
    dependencies {
      compile project(':library')
  }
  
  2.在xml中使用:
  
     <ui.RectangleButton xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/rectangle_button"
        android:layout_width="100dp"
        android:layout_height="200dp"
        custom:text="Button" />
        
  3.也可以在你的activity中修改某些属性:
  
        RectangleButton rectangleButton = (RectangleButton) findViewById(R.id.rectangle_button);
        rectangleButton.setText("Button");
        rectangleButton.setTextColor(0xffffffff);
        rectangleButton.setRippleSpeed(20f);
        rectangleButton.setRippleSize(40);
        rectangleButton.setRippleColor(0xff000000);

##说明
  作者是枚大二狗，不足之处请多多指教～求star～
