
SlidingMenu �������Խ���:
menu.setMode(SlidingMenu.LEFT);//�����󻬲˵�
menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//���û�������Ļ��Χ��������Ϊȫ�����򶼿��Ի���
menu.setShadowDrawable(R.drawable.shadow);//������ӰͼƬ
menu.setShadowWidthRes(R.dimen.shadow_width);//������ӰͼƬ�Ŀ��
menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu����ʱ��ҳ����ʾ��ʣ����
menu.setBehindWidth(400);//����SlidingMenu�˵��Ŀ��
menu.setFadeDegree(0.35f);//SlidingMenu����ʱ�Ľ���̶�
menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//ʹSlidingMenu������Activity��
menu.setMenu(R.layout.menu_layout);//����menu�Ĳ����ļ�
menu.toggle();//��̬�ж��Զ��رջ���SlidingMenu
menu.showMenu();//��ʾSlidingMenu
menu.showContent();//��ʾ����
menu.setOnOpenListener(onOpenListener);//����slidingmenu��
���ڹر�menu�������������򵥵���˵������menu close�¼���һ����when,һ����after 
menu.OnClosedListener(OnClosedListener);//����slidingmenu�ر�ʱ�¼�
menu.OnClosedListener(OnClosedListener);//����slidingmenu�رպ��¼�

���Ҷ����Ի���SlidingMenu�˵�ֻ��Ҫ����
menu.setMode(SlidingMenu.LEFT_RIGHT);���ԣ�Ȼ�������Ҳ�˵��Ĳ����ļ�
menu.setSecondaryShadowDrawable(R.drawable.shadowright);//�Ҳ�˵�����ӰͼƬ


=======================================================================================================================
ʹ��Fragmentʵ��SlidingMenu:
1.����Activity�̳���SlidingMenu���µ�SlidingFragmentActivity
2. setContentView(R.layout.content_frame);//��layoutΪһ��ȫ����FrameLayout
3. setBehindContentView(R.layout.menu_frame);//����SlidingMenuʹ�õĲ��֣�ͬ����һ��ȫ����FrameLayout
4.����SlidingMenu���˵���Fragment
     
[java code] 
setBehindContentView(R.layout.menu_frame);  
FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();  
leftMenuFragment = new MenuFragment();  
t.replace(R.id.menu_frame, leftMenuFragment);  
t.commit();  

=======================================================================================================================

