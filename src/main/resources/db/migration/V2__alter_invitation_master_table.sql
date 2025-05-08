# alter table invitation_master modify column im_bi_id bigint after im_id;
# alter table invitation_master modify column im_gi_id bigint after im_bi_id;
# alter table invitation_master modify column im_iu_id bigint after im_gi_id;
# alter table invitation_master modify column im_d_date date after im_iu_id;
# alter table invitation_master modify column reg_time datetime after im_main_text;
# alter table invitation_master modify column mod_time datetime after reg_time;
# alter table invitation_master modify column created_by varchar(255) after mod_time;
# alter table invitation_master modify column last_modified_by varchar(255) after created_by;