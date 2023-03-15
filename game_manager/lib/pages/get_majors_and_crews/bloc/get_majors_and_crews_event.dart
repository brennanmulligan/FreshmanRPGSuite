/*
 creates a majors and crews event class
 */
part of 'get_majors_and_crews_bloc.dart';
abstract class GetMajorsAndCrewsEvent{}

class SendCreateMajorsCrewEvent extends GetMajorsAndCrewsEvent {

    final num crews;
    final num  majors;


    SendCreateMajorsCrewEvent(this.crews, this.majors);
/*
returns the values major and crews event strings within SendCreateMajorsCrewEvent
 */
  String toSting() {
    return 'SendCreateMajorsCrewsEvent(crew: $crews, major: $majors)';
  }
}