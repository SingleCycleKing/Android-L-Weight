#L-Weight
  向下支持的仿制Android5.0的控件
  
  1.波纹效果的Button
  
  2.波纹效果的Layout
  
##使用方法
  1.将library导入，修改你的工程的gradle如下:

    dependencies {
      compile project(':library')
    }

  2.在xml中使用:
  ```xml
 <ui.RectangleButton xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:id="@+id/rectangle_button"
    android:layout_width="100dp"
    android:layout_height="200dp"
    custom:text="Button" />
  ```      
  3.也可以在你的activity中修改某些属性:
  ```java
 RectangleButton rectangleButton = (RectangleButton) findViewById(R.id.rectangle_button);
 rectangleButton.setText("Button");
 rectangleButton.setTextColor(0xffffffff);
 rectangleButton.setRippleSpeed(20f);
 rectangleButton.setRippleSize(40);
 rectangleButton.setRippleColor(0xff000000);
  ```
  
##Button
   ```xml
  <ui.RectangleButton xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:id="@+id/rectangle_button"
    android:layout_width="100dp"
    android:layout_height="200dp"
    custom:text="Button" />
  ``` 
  ```java
RectangleButton rectangleButton = (RectangleButton) findViewById(R.id.rectangle_button);
rectangleButton.setText("Button");
rectangleButton.setTextColor(0xffffffff);
rectangleButton.setRippleSpeed(20f);
rectangleButton.setRippleSize(40);
rectangleButton.setRippleColor(0xff000000);
```
##RippleLayout
```xml
<ui.RippleLayout
   android:id="@+id/ripple_layout"
   android:layout_width="match_parent"
   android:layout_height="wrap_content">
        
      <TextView
        android:layout_centerInParent="true"
        android:textSize="20sp"
        android:text="This is a RippleLayout."
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</ui.RippleLayout>
```
```java
RippleLayout rippleLayout = (RippleLayout) findViewById(R.id.ripple_layout);
rippleLayout.setRippleColor(0xff000000);
rippleLayout.setRippleSize(5);
rippleLayout.setRippleSpeed(20f);
```
##FloatButton
```xml
<ui.FloatButton 
    android:id="@+id/float_button"
    android:layout_alignParentBottom="true"
    android:layout_alignParentRight="true"
    android:layout_marginBottom="50dp"
    android:layout_width="50dp"
    android:layout_height="50dp" />
```
```java
 FloatButton floatButton = (FloatButton) findViewById(R.id.float_button);
 floatButton.setRippleColor(0xff000000);
 floatButton.setRippleSize(5);
 floatButton.setRippleSpeed(20f);
 floatButton.setDrawableIcon(getResources().getDrawable(R.drawable.add_icon));
```
设置进入退出动画
```java
 floatButton.floatButtonIn();
 floatButton.floatButtonOut();
```
##说明
  Coder是枚大二狗，不足之处请多多指教～求star～
