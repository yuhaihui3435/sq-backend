sample
===

	select #use("cols")#,(select SU_NAME from SYS_USER_T  where ID=carousel.CR_BY) as crByName,(select SU_NAME from SYS_USER_T  where ID=carousel.UP_BY) as upByName,(select SU_NAME from SYS_USER_T  where ID=carousel.DE_BY) as deByName from CAROUSEL_T carousel where  #use("condition")#

sample$count
===
    select count(1) from CAROUSEL_T carousel where #use("condition")#

cols
===
	carousel.IMG,carousel.SUB_TITLE,carousel.DE_AT,carousel.CR_BY,carousel.URL,carousel.UP_AT,carousel.UP_BY,carousel.ID,carousel.CR_AT,carousel.DE_BY,carousel.EFFECTIVE_AT,carousel.STATUS,carousel.TITLE,carousel.ORDER,carousel.EXPIRED_AT

updateSample
===

	carousel.IMG=#img#,carousel.SUB_TITLE=#subTitle#,carousel.DE_AT=#deAt#,carousel.CR_BY=#crBy#,carousel.URL=#url#,carousel.UP_AT=#upAt#,carousel.UP_BY=#upBy#,carousel.ID=#id#,carousel.CR_AT=#crAt#,carousel.DE_BY=#deBy#,carousel.EFFECTIVE_AT=#effectiveAt#,carousel.STATUS=#status#,carousel.TITLE=#title#,carousel.ORDER=#order#,carousel.EXPIRED_AT=#expiredAt#

condition
===

	1 = 1 and DE_AT is null
	@if(!isEmpty(img)){
	 and carousel.IMG=#img#
	@}
	@if(!isEmpty(subTitle)){
	 and carousel.SUB_TITLE=#subTitle#
	@}
	@if(!isEmpty(deAt)){
	 and carousel.DE_AT=#deAt#
	@}
	@if(!isEmpty(crBy)){
	 and carousel.CR_BY=#crBy#
	@}
	@if(!isEmpty(url)){
	 and carousel.URL=#url#
	@}
	@if(!isEmpty(upAt)){
	 and carousel.UP_AT=#upAt#
	@}
	@if(!isEmpty(upBy)){
	 and carousel.UP_BY=#upBy#
	@}
	@if(!isEmpty(id)){
	 and carousel.ID=#id#
	@}
	@if(!isEmpty(crAt)){
	 and carousel.CR_AT=#crAt#
	@}
	@if(!isEmpty(deBy)){
	 and carousel.DE_BY=#deBy#
	@}
	@if(!isEmpty(effectiveAt)){
	 and carousel.EFFECTIVE_AT=#effectiveAt#
	@}
	@if(!isEmpty(status)){
	 and carousel.STATUS=#status#
	@}
	@if(!isEmpty(title)){
	 and carousel.TITLE=#title#
	@}
	@if(!isEmpty(order)){
	 and carousel.ORDER=#order#
	@}
	@if(!isEmpty(expiredAt)){
	 and carousel.EXPIRED_AT=#expiredAt#
	@}



