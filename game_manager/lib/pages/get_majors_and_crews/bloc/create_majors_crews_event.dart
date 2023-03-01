
abstract class CreateMajorsCrewsEvent{}

class SendCreateMajorsCrewEvent extends CreateMajorsCrewsEvent {

    final num crews;
    final num  majors;


  SendCreateMajorsCrewEvent(this.crews, this.majors);

  String toSting() {
    return 'SendCreateMajorsCrewsEvent(crew: $crews, major: $majors)';
  }
}