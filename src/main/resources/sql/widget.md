sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=widget.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=widget.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=widget.UP_BY) as upByName from WIDGET_T widget where  #use("condition")#

sample$count
===
    select count(1) from WIDGET_T widget where #use("condition")#

cols
===
	widget.CR_BY,widget.UP_AT,widget.DFT_W,widget.STATUS,widget.DEF_H,widget.ID,widget.CODE,widget.TYPE,widget.DE_AT,widget.TITLE,widget.URL,widget.DE_BY,widget.CR_AT,widget.REMARK,widget.UP_BY

updateSample
===

	widget.CR_BY=#crBy#,widget.UP_AT=#upAt#,widget.DFT_W=#dftW#,widget.STATUS=#status#,widget.DEF_H=#defH#,widget.ID=#id#,widget.CODE=#code#,widget.TYPE=#type#,widget.DE_AT=#deAt#,widget.TITLE=#title#,widget.URL=#url#,widget.DE_BY=#deBy#,widget.CR_AT=#crAt#,widget.REMARK=#remark#,widget.UP_BY=#upBy#

queryWidgetByRole
=== 
    select distinct rm.*, m.*
      from role_widget_t rm
      left join widget_t m
        on rm.widget_id = m.id
     where rm.role_id in (
     @for(id in ids){
      #id#  #text(idLP.last?"":"," )#
      @}
     )
     and rm.de_at is null
     order by m.cr_at
     
queryWidgetByUser
=== 
    select distinct rm.*, m.*
      from user_widget_t rm
      left join widget_t m
        on rm.widget_id = m.id
     where rm.sys_user_id=#userId#
     and rm.de_at is null
     order by m.cr_at
     
queryWidgetByRoleDis
=== 
    select distinct m.*
      from role_widget_t rm
      left join widget_t m
        on rm.widget_id = m.id
     where rm.role_id in (
     @for(id in ids){
      #id#  #text(idLP.last?"":"," )#
      @}
     )
     and rm.de_at is null
     order by m.cr_at
     
queryUserWidgetByRole
=== 
    select distinct rm.*, m.*
    from user_widget_t rm
    left join widget_t m
    on rm.widget_id = m.id
    where rm.sys_user_id = #userId#
    and rm.type=0
    order by m.cr_at

queryWidgetLinkByUserId
===
    select t.*,wt.code from USER_WIDGET_T t left join widget_t wt on t.widget_id=wt.id 
    where t.de_at is null and t.sys_user_id=#userId#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(crBy)){
	 and widget.CR_BY=#crBy#
	@}
	@if(!isEmpty(upAt)){
	 and widget.UP_AT=#upAt#
	@}
	@if(!isEmpty(dftW)){
	 and widget.DFT_W=#dftW#
	@}
	@if(!isEmpty(status)){
	 and widget.STATUS=#status#
	@}
	@if(!isEmpty(defH)){
	 and widget.DEF_H=#defH#
	@}
	@if(!isEmpty(id)){
	 and widget.ID=#id#
	@}
	@if(!isEmpty(code)){
	 and widget.CODE=#code#
	@}
	@if(!isEmpty(type)){
	 and widget.TYPE=#type#
	@}
	@if(!isEmpty(deAt)){
	 and widget.DE_AT=#deAt#
	@}
	@if(!isEmpty(title)){
	 and widget.TITLE=#title#
	@}
	@if(!isEmpty(url)){
	 and widget.URL=#url#
	@}
	@if(!isEmpty(deBy)){
	 and widget.DE_BY=#deBy#
	@}
	@if(!isEmpty(crAt)){
	 and widget.CR_AT=#crAt#
	@}
	@if(!isEmpty(remark)){
	 and widget.REMARK=#remark#
	@}
	@if(!isEmpty(upBy)){
	 and widget.UP_BY=#upBy#
	@}



