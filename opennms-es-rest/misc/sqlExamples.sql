CREATE OR REPLACE FUNCTION notify_alarm() RETURNS TRIGGER AS $$

    DECLARE 
        new_data json;
        old_data json;
        notification text= '[{},{}]';
    
    BEGIN

/*
        -- create a json array and populate with [NEW_row_json,OLD_row_json]
        -- Convert the old or new row to JSON, based on the kind of action.
        -- Action = DELETE              -> [empty_object,OLD_row_json]
        -- Action = INSERT              -> [NEW_row_json,empty_object]
        -- Action = UPDATE              -> [NEW_row_json,OLD_row_json]
*/
	    
	    
	    IF (TG_OP = 'INSERT') THEN 
	        new_data=row_to_json(NEW);
	        notification=CONCAT('[', new_data::text, ', {}]');
	        PERFORM pg_notify('opennms_alarm_changes',notification);
	        
        ELSEIF (TG_OP = 'DELETE') THEN
            old_data=row_to_json(OLD);
	        notification=CONCAT('[{}, ', old_data::text, ']');
	        PERFORM pg_notify('opennms_alarm_changes',notification);
            
        ELSE
        /* IGNORE rows where only event count updated*/
            new_data=row_to_json(NEW);
            old_data=row_to_json(OLD);
            notification=CONCAT('[', new_data::text,',', old_data::text, ']');
            
        END IF;

        PERFORM pg_notify('opennms_alarm_changes',notification);
        
/*
        -- Result is ignored since this is an AFTER trigger
*/
        RETURN NULL; 
    END;
    
$$ LANGUAGE plpgsql;

 DROP TRIGGER IF EXISTS alarms_change_notify ON alarms;

 CREATE TRIGGER alarms_change_notify
 AFTER INSERT OR UPDATE OR DELETE ON alarms
    FOR EACH ROW EXECUTE PROCEDURE notify_alarm();
    
  