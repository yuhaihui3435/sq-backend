sample
===

	select #use("cols")#,
	(select SU_NAME from SYS_USER_T  where ID=artice.UP_BY) as upByName,
	(select SU_NAME from SYS_USER_T  where ID=artice.CR_BY) as crByName,
	(select SU_NAME from SYS_USER_T  where ID=artice.DE_BY) as deByName 
	from ARTICE_T artice LEFT JOIN artice_tag_t tag ON artice.ID=tag.ARTICE_ID where 
	
	@if(!isEmpty(columnIds)){
            	 1=1 and artice.COLUMN_ID in (
            	    @for(id in columnIds){
                       #id#  #text(idLP.last?"":"," )#
                    @}
                 )
                 and
            @}
	
	@if(!isEmpty(tagId)){
        	 1=1 and tag.tag_id in (
        	    @for(id in tagId){
                   #id#  #text(idLP.last?"":"," )#
                @}
             )
             and
        @}
    #use("condition")#
    @orm.many({"id":"articeId"},"artice.artice.selectArticeTag","ArticeTag");
	@orm.single({"columnId":"cId"},"artice.column.selectColumnAllParent","Column");

sample$count
===
    select count(1) 
     from ARTICE_T artice LEFT JOIN artice_tag_t tag ON artice.ID=tag.ARTICE_ID where 
     	
     	@if(!isEmpty(columnIds)){
                 	 1=1 and artice.COLUMN_ID in (
                 	    @for(id in columnIds){
                            #id#  #text(idLP.last?"":"," )#
                         @}
                      )
                      and
                 @}
     	
     	@if(!isEmpty(tagId)){
             	 1=1 and tag.tag_id in (
             	    @for(id in tagId){
                        #id#  #text(idLP.last?"":"," )#
                     @}
                  )
                  and
             @}
      #use("condition")#

cols
===
	artice.URL,artice.LANGUAGE,artice.BIG_COVER_PIC,artice.TOP_SHOW,artice.ORIGIN,artice.ID,artice.READING_TIME,artice.EFFECT,artice.COLUMN_ID,artice.TPL_NAME,artice.TITLE,artice.AUTHOR,artice.EXPIRED,artice.UP_BY,artice.SUMMARY_EN,artice.DETAIL,artice.PUBLISH_STATUS,artice.DE_AT,artice.COVER_PIC,artice.REPORT_STATUS,artice.CR_BY,artice.CR_AT,artice.DE_BY,artice.UP_AT,artice.TOP,artice.LEAVE_MSG_STATUS,artice.TITLE_EN,artice.DETAIL_EN,artice.SUMMARY,artice.REQ_MEMBER

updateSample
===

	artice.URL=#url#,artice.LANGUAGE=#language#,artice.BIG_COVER_PIC=#bigCoverPic#,artice.TOP_SHOW=#topShow#,artice.ORIGIN=#origin#,artice.ID=#id#,artice.READING_TIME=#readingTime#,artice.EFFECT=#effect#,artice.COLUMN_ID=#columnId#,artice.TPL_NAME=#tplName#,artice.TITLE=#title#,artice.AUTHOR=#author#,artice.EXPIRED=#expired#,artice.UP_BY=#upBy#,artice.SUMMARY_EN=#summaryEn#,artice.DETAIL=#detail#,artice.PUBLISH_STATUS=#publishStatus#,artice.DE_AT=#deAt#,artice.COVER_PIC=#coverPic#,artice.REPORT_STATUS=#reportStatus#,artice.CR_BY=#crBy#,artice.CR_AT=#crAt#,artice.DE_BY=#deBy#,artice.UP_AT=#upAt#,artice.TOP=#top#,artice.LEAVE_MSG_STATUS=#leaveMsgStatus#,artice.TITLE_EN=#titleEn#,artice.DETAIL_EN=#detailEn#,artice.SUMMARY=#summary#,
	artice.REQ_REMBER=#reqMember#

condition
===

	 1=1 and DE_AT is null
	@if(!isEmpty(reqMember)){
     and artice.REQ_MEMBER=#reqMember#
    @}
	@if(!isEmpty(origin)){
     and artice.ORIGIN=#origin#
    @}
	@if(!isEmpty(id)){
	 and artice.ID=#id#
	@}
	@if(!isEmpty(readingTime)){
	 and artice.READING_TIME=#readingTime#
	@}
	@if(!isEmpty(effect)){
	 and artice.EFFECT=#effect#
	@}
	@if(!isEmpty(columnId)&&columnId!=0){
	 and artice.COLUMN_ID=#columnId#
	@}
	@if(!isEmpty(tplName)){
	 and artice.TPL_NAME=#tplName#
	@}
	@if(!isEmpty(title)){
	 and (artice.TITLE like #'%'+title+'%'# )
	@}
	@if(!isEmpty(author)){
	 and artice.AUTHOR=#author#
	@}
	@if(!isEmpty(expired)){
	 and artice.EXPIRED=#expired#
	@}
	@if(!isEmpty(upBy)){
	 and artice.UP_BY=#upBy#
	@}
	@if(!isEmpty(summaryEn)){
	 and artice.SUMMARY_EN=#summaryEn#
	@}
	@if(!isEmpty(detail)){
	 and artice.DETAIL=#detail#
	@}
	@if(!isEmpty(publishStatus)){
	 and artice.PUBLISH_STATUS=#publishStatus#
	@}
	@if(!isEmpty(deAt)){
	 and artice.DE_AT=#deAt#
	@}
	@if(!isEmpty(coverPic)){
	 and artice.COVER_PIC=#coverPic#
	@}
	@if(!isEmpty(reportStatus)){
	 and artice.REPORT_STATUS=#reportStatus#
	@}
	@if(!isEmpty(crBy)){
	 and artice.CR_BY=#crBy#
	@}
	@if(!isEmpty(crAt)){
	 and artice.CR_AT=#crAt#
	@}
	@if(!isEmpty(deBy)){
	 and artice.DE_BY=#deBy#
	@}
	@if(!isEmpty(upAt)){
	 and artice.UP_AT=#upAt#
	@}
	@if(!isEmpty(top)){
	 and artice.TOP=#top#
	@}
	@if(!isEmpty(leaveMsgStatus)){
	 and artice.LEAVE_MSG_STATUS=#leaveMsgStatus#
	@}
	@if(!isEmpty(titleEn)){
	 and artice.TITLE_EN=#titleEn#
	@}
	@if(!isEmpty(detailEn)){
	 and artice.DETAIL_EN=#detailEn#
	@}
	@if(!isEmpty(language)){
    	 and artice.LANGUAGE=#language#
    	@}
	@if(!isEmpty(summary)){
	 and artice.SUMMARY=#summary#
	@}
    @if(!isEmpty(topShow)){
	 and artice.TOP_SHOW=#topShow#
	@}
	order by artice.UP_AT desc

selectArticeTag
===
    select articeTag.ID,articeTag.ARTICE_ID,articeTag.TAG_ID from ARTICE_TAG_T articeTag where 1 = 1
     and articeTag.ARTICE_ID=#articeId#
     @orm.single({"tagId":"id"},"com.neuray.wp.entity.DictItem");

miniCols
===
	artice.URL,artice.ID,artice.TITLE,artice.AUTHOR,artice.UP_AT,artice.CR_AT,artice.SUMMARY,artice.REQ_MEMBER,artice.COVER_PIC,artice.ORIGIN

selectByField
===
	select #use("miniCols")# 
	from ARTICE_T artice where 
	
	@if(!isEmpty(columnIds)){
            	 1=1 and artice.COLUMN_ID in (
            	    @for(id in columnIds){
                       #id#  #text(idLP.last?"":"," )#
                    @}
                 )
                 and
            @}
	#use("condition")#
	@orm.single({"columnId":"cId"},"artice.column.selectColumnAllParent","Column");

selectByField$count
===
    select count(1) from ARTICE_T artice  where 
     	@if(!isEmpty(columnIds)){
                 	 1=1 and artice.COLUMN_ID in (
                 	    @for(id in columnIds){
                            #id#  #text(idLP.last?"":"," )#
                         @}
                      )
                      and
                 @}
      #use("condition")#

selectByTopColumn
===
	select  #use("miniCols")# 
	from ARTICE_T artice where artice.column_id in 
	(
		select id from
			(
			select
				id
			from
			(
				select
				t1.id,
				if(find_in_set(parent_id, \@pids) > 0,
				\@pids := concat(\@pids, ',', id),
				0) as ischild
			from
				(
				select
					id,
					parent_id
				from
					column_t t
				where
					t.de_at is null
				order by
					parent_id,
					id ) t1,
				(
				select
					\@pids := #columnId# ) t2 ) t3
		where
			ischild != 0) t4
		where
			id not in(
			select
				distinct (`PARENT_ID`)
			from
				column_t ct
			where
				ct.`DE_AT` is null
				and ct.`PARENT_ID` is not null )
			)
		and publish_status='00' and de_at is null  order by up_at desc LIMIT #size# 