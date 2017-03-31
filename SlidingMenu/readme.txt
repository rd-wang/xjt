
SlidingMenu 常用属性介绍:
menu.setMode(SlidingMenu.LEFT);//设置左滑菜单
menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑动的屏幕范围，该设置为全屏区域都可以滑动
menu.setShadowDrawable(R.drawable.shadow);//设置阴影图片
menu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影图片的宽度
menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu划出时主页面显示的剩余宽度
menu.setBehindWidth(400);//设置SlidingMenu菜单的宽度
menu.setFadeDegree(0.35f);//SlidingMenu滑动时的渐变程度
menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
menu.setMenu(R.layout.menu_layout);//设置menu的布局文件
menu.toggle();//动态判断自动关闭或开启SlidingMenu
menu.showMenu();//显示SlidingMenu
menu.showContent();//显示内容
menu.setOnOpenListener(onOpenListener);//监听slidingmenu打开
关于关闭menu有两个监听，简单的来说，对于menu close事件，一个是when,一个是after 
menu.OnClosedListener(OnClosedListener);//监听slidingmenu关闭时事件
menu.OnClosedListener(OnClosedListener);//监听slidingmenu关闭后事件

左右都可以划出SlidingMenu菜单只需要设置
menu.setMode(SlidingMenu.LEFT_RIGHT);属性，然后设置右侧菜单的布局文件
menu.setSecondaryShadowDrawable(R.drawable.shadowright);//右侧菜单的阴影图片


=======================================================================================================================
使用Fragment实现SlidingMenu:
1.首先Activity继承自SlidingMenu包下的SlidingFragmentActivity
2. setContentView(R.layout.content_frame);//该layout为一个全屏的FrameLayout
3. setBehindContentView(R.layout.menu_frame);//设置SlidingMenu使用的布局，同样是一个全屏的FrameLayout
4.设置SlidingMenu左侧菜单的Fragment
     
[java code] 
setBehindContentView(R.layout.menu_frame);  
FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();  
leftMenuFragment = new MenuFragment();  
t.replace(R.id.menu_frame, leftMenuFragment);  
t.commit();  

=======================================================================================================================

