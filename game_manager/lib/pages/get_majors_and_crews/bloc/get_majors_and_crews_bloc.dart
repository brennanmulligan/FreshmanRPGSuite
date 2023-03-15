import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/cupertino.dart';
import 'package:game_manager/repository/player/basic_response.dart';

part 'get_majors_and_crews_event.dart';
part 'get_majors_and_crews_state.dart';

class GetMajorsAndCrewsBloc extends Bloc<GetMajorsAndCrewsEvent, GetMajorsAndCrewsState> {
  late GetMajorsAndCrewsRequest data;
  final GetMajorsAndCrewsRepository;

  GetMajorsAndCrewsBloc({
    required this.GetMajorsAndCrewsRepository
  }) : super(GetMajorsAndCrewsInitial()) {
    on<SendCreateMajorsCrewEvent>((event, emit) async {
      emit(GetMajorsAndCrewsLoading());
      GetMajorsAndCrewsResponse response = await GetMajorsAndCrewsRepository.getMajorsAndCrews();

      if (response.success) {
        emit(GetMajorsAndCrewsSuccess(response));
      } else {
        emit(GetMajorsAndCrewsFail());
      }
    });
  }
}