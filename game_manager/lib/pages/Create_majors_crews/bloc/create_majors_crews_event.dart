
abstract class CreateMajorsCrewsEvent{}

class SendCreateMajorsCrewEvent extends CreateMajorsCrewsEvent {

    var crews;
    var  majors;


  SendCreateMajorsCrewEvent(this.crews, this.majors);

  String toSting() {
    return 'SendCreateMajorsCrewsEvent(crew: $crews, major: $majors)';
  }
}