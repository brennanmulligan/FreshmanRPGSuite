
abstract class CreateMajorsCrewsEvent{}

class SendCreateMajorsCrewEvent extends CreateMajorsCrewsEvent {
  final num crew;
  final num major;
  final num section;

  SendCreateMajorsCrewEvent(this.crew, this.major, this.section);

  String toSting() {
    return 'SendCreateMajorsCrewsEvent(crew: $crew, major: $major, section: $section)';
  }
}