ALTER TABLE image_uploads DROP FOREIGN KEY FKrxc9u2h2v49lo9daoh55lebpu;
ALTER TABLE image_uploads
    ADD CONSTRAINT FKrxc9u2h2v49lo9daoh55lebpu
        FOREIGN KEY (iu_pm_id) REFERENCES pet_master(pm_id)
            ON DELETE SET NULL;