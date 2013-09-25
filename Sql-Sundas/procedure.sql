CREATE OR REPLACE PROCEDURE undoStep(tillDate IN DATE) IS

cursor c_tab is Select StudId, SemesterID, CourseID, columnName, old_value from stagingTable where changeTime >= tillDate;

BEGIN


for v_c in c_tab loop

   if(v_c.columnName = 'SecNo') then
      update CourseAllocation set SecNo = v_c.old_value where StudId = v_c.StudId and SemesterID = v_c.SemesterID and CourseID = v_c.CourseID;
   end if;
   
   if(v_c.columnName = 'BNo') then
      update CourseAllocation set BNo = v_c.old_value where StudId = v_c.StudId and SemesterID = v_c.SemesterID and CourseID = v_c.CourseID;
   end if;
   
   if(v_c.columnName = 'RoomNo') then
      update CourseAllocation set RoomNo = v_c.old_value where StudId = v_c.StudId and SemesterID = v_c.SemesterID and CourseID = v_c.CourseID;
   end if;
   
   if(v_c.columnName = 'Marks') then
      update CourseAllocation set Marks = v_c.old_value where StudId = v_c.StudId and SemesterID = v_c.SemesterID and CourseID = v_c.CourseID;
   end if;
   
   if(v_c.columnName = 'LGrade') then
      update CourseAllocation set LGrade = v_c.old_value where StudId = v_c.StudId and SemesterID = v_c.SemesterID and CourseID = v_c.CourseID;
   end if;
end loop;

END;
