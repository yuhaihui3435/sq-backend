sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=roleWidget.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=roleWidget.DE_BY) as deByName,(select SU_NAME from SYS_USER_T  where ID=roleWidget.CR_BY) as crByName from ROLE_WIDGET_T roleWidget where  #use("condition")#

sample$count
===
    select count(1) from ROLE_WIDGET_T roleWidget where #use("condition")#

cols
===
	roleWidget.EFFECT,roleWidget.DE_AT,roleWidget.UP_BY,roleWidget.ROLE_ID,roleWidget.DE_BY,roleWidget.EXPIRED,roleWidget.CR_AT,roleWidget.CR_BY,roleWidget.UP_AT,roleWidget.WIDGET_ID

updateSample
===

	roleWidget.EFFECT=#effect#,roleWidget.DE_AT=#deAt#,roleWidget.UP_BY=#upBy#,roleWidget.ROLE_ID=#roleId#,roleWidget.DE_BY=#deBy#,roleWidget.EXPIRED=#expired#,roleWidget.CR_AT=#crAt#,roleWidget.CR_BY=#crBy#,roleWidget.UP_AT=#upAt#,roleWidget.WIDGET_ID=#widgetId#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(effect)){
	 and roleWidget.EFFECT=#effect#
	@}
	@if(!isEmpty(deAt)){
	 and roleWidget.DE_AT=#deAt#
	@}
	@if(!isEmpty(upBy)){
	 and roleWidget.UP_BY=#upBy#
	@}
	@if(!isEmpty(roleId)){
	 and roleWidget.ROLE_ID=#roleId#
	@}
	@if(!isEmpty(deBy)){
	 and roleWidget.DE_BY=#deBy#
	@}
	@if(!isEmpty(expired)){
	 and roleWidget.EXPIRED=#expired#
	@}
	@if(!isEmpty(crAt)){
	 and roleWidget.CR_AT=#crAt#
	@}
	@if(!isEmpty(crBy)){
	 and roleWidget.CR_BY=#crBy#
	@}
	@if(!isEmpty(upAt)){
	 and roleWidget.UP_AT=#upAt#
	@}
	@if(!isEmpty(widgetId)){
	 and roleWidget.WIDGET_ID=#widgetId#
	@}



