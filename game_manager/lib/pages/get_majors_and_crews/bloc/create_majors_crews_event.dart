/*
 creates a majors and crews event class
 */
abstract class CreateMajorsCrewsEvent{}

class SendCreateMajorsCrewEvent extends CreateMajorsCrewsEvent {

    final num crews;
    final num  majors;


    SendCreateMajorsCrewEvent(this.crews, this.majors);
/*
returns the major and crews event string
 */
  String toSting() {
    return 'SendCreateMajorsCrewsEvent(crew: $crews, major: $majors)';
  }
}