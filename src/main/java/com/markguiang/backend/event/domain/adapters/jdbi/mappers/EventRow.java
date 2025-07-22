package com.markguiang.backend.event.domain.adapters.jdbi.mappers;

import java.sql.Timestamp;
import java.util.UUID;

public class EventRow {
  public UUID event_id;
  public String name;
  public Boolean has_multiple_location;
  public String description;
  public String location;
  public String img_url;
  public String status;

  public UUID day_id;
  public String day_location;
  public Timestamp day_date;

  public Timestamp agenda_start;
  public Timestamp agenda_end;
  public String agenda_location;
}
