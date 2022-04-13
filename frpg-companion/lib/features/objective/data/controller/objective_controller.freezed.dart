// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'objective_controller.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$ObjectiveStateTearOff {
  const _$ObjectiveStateTearOff();

  _ObjectiveState call(
      {dynamic showResponse = false,
      dynamic playerID = 0,
      dynamic questID = 0,
      dynamic objectiveID = 0,
      dynamic playerName = "",
      CompleteObjectiveResponse? objectiveResponse}) {
    return _ObjectiveState(
      showResponse: showResponse,
      playerID: playerID,
      questID: questID,
      objectiveID: objectiveID,
      playerName: playerName,
      objectiveResponse: objectiveResponse,
    );
  }
}

/// @nodoc
const $ObjectiveState = _$ObjectiveStateTearOff();

/// @nodoc
mixin _$ObjectiveState {
  dynamic get showResponse => throw _privateConstructorUsedError;
  dynamic get playerID => throw _privateConstructorUsedError;
  dynamic get questID => throw _privateConstructorUsedError;
  dynamic get objectiveID => throw _privateConstructorUsedError;
  dynamic get playerName => throw _privateConstructorUsedError;
  CompleteObjectiveResponse? get objectiveResponse =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ObjectiveStateCopyWith<ObjectiveState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ObjectiveStateCopyWith<$Res> {
  factory $ObjectiveStateCopyWith(
          ObjectiveState value, $Res Function(ObjectiveState) then) =
      _$ObjectiveStateCopyWithImpl<$Res>;
  $Res call(
      {dynamic showResponse,
      dynamic playerID,
      dynamic questID,
      dynamic objectiveID,
      dynamic playerName,
      CompleteObjectiveResponse? objectiveResponse});
}

/// @nodoc
class _$ObjectiveStateCopyWithImpl<$Res>
    implements $ObjectiveStateCopyWith<$Res> {
  _$ObjectiveStateCopyWithImpl(this._value, this._then);

  final ObjectiveState _value;
  // ignore: unused_field
  final $Res Function(ObjectiveState) _then;

  @override
  $Res call({
    Object? showResponse = freezed,
    Object? playerID = freezed,
    Object? questID = freezed,
    Object? objectiveID = freezed,
    Object? playerName = freezed,
    Object? objectiveResponse = freezed,
  }) {
    return _then(_value.copyWith(
      showResponse: showResponse == freezed
          ? _value.showResponse
          : showResponse // ignore: cast_nullable_to_non_nullable
              as dynamic,
      playerID: playerID == freezed
          ? _value.playerID
          : playerID // ignore: cast_nullable_to_non_nullable
              as dynamic,
      questID: questID == freezed
          ? _value.questID
          : questID // ignore: cast_nullable_to_non_nullable
              as dynamic,
      objectiveID: objectiveID == freezed
          ? _value.objectiveID
          : objectiveID // ignore: cast_nullable_to_non_nullable
              as dynamic,
      playerName: playerName == freezed
          ? _value.playerName
          : playerName // ignore: cast_nullable_to_non_nullable
              as dynamic,
      objectiveResponse: objectiveResponse == freezed
          ? _value.objectiveResponse
          : objectiveResponse // ignore: cast_nullable_to_non_nullable
              as CompleteObjectiveResponse?,
    ));
  }
}

/// @nodoc
abstract class _$ObjectiveStateCopyWith<$Res>
    implements $ObjectiveStateCopyWith<$Res> {
  factory _$ObjectiveStateCopyWith(
          _ObjectiveState value, $Res Function(_ObjectiveState) then) =
      __$ObjectiveStateCopyWithImpl<$Res>;
  @override
  $Res call(
      {dynamic showResponse,
      dynamic playerID,
      dynamic questID,
      dynamic objectiveID,
      dynamic playerName,
      CompleteObjectiveResponse? objectiveResponse});
}

/// @nodoc
class __$ObjectiveStateCopyWithImpl<$Res>
    extends _$ObjectiveStateCopyWithImpl<$Res>
    implements _$ObjectiveStateCopyWith<$Res> {
  __$ObjectiveStateCopyWithImpl(
      _ObjectiveState _value, $Res Function(_ObjectiveState) _then)
      : super(_value, (v) => _then(v as _ObjectiveState));

  @override
  _ObjectiveState get _value => super._value as _ObjectiveState;

  @override
  $Res call({
    Object? showResponse = freezed,
    Object? playerID = freezed,
    Object? questID = freezed,
    Object? objectiveID = freezed,
    Object? playerName = freezed,
    Object? objectiveResponse = freezed,
  }) {
    return _then(_ObjectiveState(
      showResponse:
          showResponse == freezed ? _value.showResponse : showResponse,
      playerID: playerID == freezed ? _value.playerID : playerID,
      questID: questID == freezed ? _value.questID : questID,
      objectiveID: objectiveID == freezed ? _value.objectiveID : objectiveID,
      playerName: playerName == freezed ? _value.playerName : playerName,
      objectiveResponse: objectiveResponse == freezed
          ? _value.objectiveResponse
          : objectiveResponse // ignore: cast_nullable_to_non_nullable
              as CompleteObjectiveResponse?,
    ));
  }
}

/// @nodoc

class _$_ObjectiveState extends _ObjectiveState {
  const _$_ObjectiveState(
      {this.showResponse = false,
      this.playerID = 0,
      this.questID = 0,
      this.objectiveID = 0,
      this.playerName = "",
      this.objectiveResponse})
      : super._();

  @JsonKey()
  @override
  final dynamic showResponse;
  @JsonKey()
  @override
  final dynamic playerID;
  @JsonKey()
  @override
  final dynamic questID;
  @JsonKey()
  @override
  final dynamic objectiveID;
  @JsonKey()
  @override
  final dynamic playerName;
  @override
  final CompleteObjectiveResponse? objectiveResponse;

  @override
  String toString() {
    return 'ObjectiveState(showResponse: $showResponse, playerID: $playerID, questID: $questID, objectiveID: $objectiveID, playerName: $playerName, objectiveResponse: $objectiveResponse)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _ObjectiveState &&
            const DeepCollectionEquality()
                .equals(other.showResponse, showResponse) &&
            const DeepCollectionEquality().equals(other.playerID, playerID) &&
            const DeepCollectionEquality().equals(other.questID, questID) &&
            const DeepCollectionEquality()
                .equals(other.objectiveID, objectiveID) &&
            const DeepCollectionEquality()
                .equals(other.playerName, playerName) &&
            const DeepCollectionEquality()
                .equals(other.objectiveResponse, objectiveResponse));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(showResponse),
      const DeepCollectionEquality().hash(playerID),
      const DeepCollectionEquality().hash(questID),
      const DeepCollectionEquality().hash(objectiveID),
      const DeepCollectionEquality().hash(playerName),
      const DeepCollectionEquality().hash(objectiveResponse));

  @JsonKey(ignore: true)
  @override
  _$ObjectiveStateCopyWith<_ObjectiveState> get copyWith =>
      __$ObjectiveStateCopyWithImpl<_ObjectiveState>(this, _$identity);
}

abstract class _ObjectiveState extends ObjectiveState {
  const factory _ObjectiveState(
      {dynamic showResponse,
      dynamic playerID,
      dynamic questID,
      dynamic objectiveID,
      dynamic playerName,
      CompleteObjectiveResponse? objectiveResponse}) = _$_ObjectiveState;
  const _ObjectiveState._() : super._();

  @override
  dynamic get showResponse;
  @override
  dynamic get playerID;
  @override
  dynamic get questID;
  @override
  dynamic get objectiveID;
  @override
  dynamic get playerName;
  @override
  CompleteObjectiveResponse? get objectiveResponse;
  @override
  @JsonKey(ignore: true)
  _$ObjectiveStateCopyWith<_ObjectiveState> get copyWith =>
      throw _privateConstructorUsedError;
}
