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
returns the major and crews event string
 */
  String toSting() {
    return 'SendCreateMajorsCrewsEvent(crew: $crews, major: $majors)';
  }
}