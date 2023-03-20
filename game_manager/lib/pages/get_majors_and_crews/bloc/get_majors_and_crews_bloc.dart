import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';
import 'package:game_manager/repository/player/basic_response.dart';
import 'package:game_manager/repository/player/all_crews_request.dart';
import 'package:game_manager/repository/player/all_majors_request.dart';
import 'package:game_manager/repository/player/all_crews_response.dart';
import 'package:game_manager/repository/player/all_majors_response.dart';

part 'get_majors_and_crews_event.dart';
part 'get_majors_and_crews_state.dart';

class GetMajorsAndCrewsBloc extends Bloc<GetMajorsAndCrewsEvent, GetMajorsAndCrewsState> {
  late AllMajorsRequest majorsData;
  late AllCrewsRequest crewsData;
  final CrewsRepository;
  final MajorsRepository;

  GetMajorsAndCrewsBloc({
    required this.CrewsRepository,
    required this.MajorsRepository,
  }) : super(GetMajorsAndCrewsInitial()) {
    on<SendCreateMajorsCrewEvent>((event, emit) async {
      emit(GetMajorsAndCrewsLoading());
      AllCrewsResponse crewsResponse = await CrewsRepository.getAllCrews(const AllCrewsRequest());
      AllMajorsResponse majorsResponse = await MajorsRepository.getAllMajors(const AllMajorsRequest());

      emit(GetMajorCrewComplete(majorsResponse, crewsResponse));
      //emit(GetCrewComplete(crewsResponse));
      //emit(GetMajorComplete(majorsResponse));
    });
  }
}