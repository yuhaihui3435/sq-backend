sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=userWidget.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=userWidget.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=userWidget.UP_BY) as upByName from USER_WIDGET_T userWidget where  #use("condition")#

sample$count
===
    select count(1) from USER_WIDGET_T userWidget where #use("condition")#

cols
===
	userWidget.WIDGET_ID,userWidget.EFFECT,userWidget.EXPIRED,userWidget.DEF_H,userWidget.DE_BY,userWidget.UP_AT,userWidget.TYPE,userWidget.CR_BY,userWidget.ID,userWidget.CR_AT,userWidget.DFT_W,userWidget.POINT_X,userWidget.UP_BY,userWidget.DE_AT,userWidget.SYS_USER_ID,userWidget.ICON,userWidget.REFRESH_RATE,userWidget.POINT_Y

updateSample
===

	userWidget.WIDGET_ID=#widgetId#,userWidget.EFFECT=#effect#,userWidget.EXPIRED=#expired#,userWidget.DEF_H=#defH#,userWidget.DE_BY=#deBy#,userWidget.UP_AT=#upAt#,userWidget.TYPE=#type#,userWidget.CR_BY=#crBy#,userWidget.ID=#id#,userWidget.CR_AT=#crAt#,userWidget.DFT_W=#dftW#,userWidget.POINT_X=#pointX#,userWidget.UP_BY=#upBy#,userWidget.DE_AT=#deAt#,userWidget.SYS_USER_ID=#sysUserId#,userWidget.ICON=#icon#,userWidget.REFRESH_RATE=#refreshRate#,userWidget.POINT_Y=#pointY#

queryUserWidgetLinkByCode
===

    select t.* from USER_WIDGET_T t left join widget_t wt on t.widget_id=wt.id 
    where t.de_at is null and t.sys_user_id=#userId# and wt.code=#code#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(widgetId)){
	 and userWidget.WIDGET_ID=#widgetId#
	@}
	@if(!isEmpty(effect)){
	 and userWidget.EFFECT=#effect#
	@}
	@if(!isEmpty(expired)){
	 and userWidget.EXPIRED=#expired#
	@}
	@if(!isEmpty(defH)){
	 and userWidget.DEF_H=#defH#
	@}
	@if(!isEmpty(deBy)){
	 and userWidget.DE_BY=#deBy#
	@}
	@if(!isEmpty(upAt)){
	 and userWidget.UP_AT=#upAt#
	@}
	@if(!isEmpty(type)){
	 and userWidget.TYPE=#type#
	@}
	@if(!isEmpty(crBy)){
	 and userWidget.CR_BY=#crBy#
	@}
	@if(!isEmpty(id)){
	 and userWidget.ID=#id#
	@}
	@if(!isEmpty(crAt)){
	 and userWidget.CR_AT=#crAt#
	@}
	@if(!isEmpty(dftW)){
	 and userWidget.DFT_W=#dftW#
	@}
	@if(!isEmpty(pointX)){
	 and userWidget.POINT_X=#pointX#
	@}
	@if(!isEmpty(upBy)){
	 and userWidget.UP_BY=#upBy#
	@}
	@if(!isEmpty(deAt)){
	 and userWidget.DE_AT=#deAt#
	@}
	@if(!isEmpty(sysUserId)){
	 and userWidget.SYS_USER_ID=#sysUserId#
	@}
	@if(!isEmpty(icon)){
	 and userWidget.ICON=#icon#
	@}
	@if(!isEmpty(refreshRate)){
	 and userWidget.REFRESH_RATE=#refreshRate#
	@}
	@if(!isEmpty(pointY)){
	 and userWidget.POINT_Y=#pointY#
	@}



