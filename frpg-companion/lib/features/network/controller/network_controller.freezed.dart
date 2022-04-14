// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'network_controller.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more informations: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
class _$NetworkStateTearOff {
  const _$NetworkStateTearOff();

  _NetworkState call(
      {dynamic baseURL = '',
      dynamic latitude = -999,
      dynamic longitude = -999}) {
    return _NetworkState(
      baseURL: baseURL,
      latitude: latitude,
      longitude: longitude,
    );
  }
}

/// @nodoc
const $NetworkState = _$NetworkStateTearOff();

/// @nodoc
mixin _$NetworkState {
  dynamic get baseURL => throw _privateConstructorUsedError;
  dynamic get latitude => throw _privateConstructorUsedError;
  dynamic get longitude => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $NetworkStateCopyWith<NetworkState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $NetworkStateCopyWith<$Res> {
  factory $NetworkStateCopyWith(
          NetworkState value, $Res Function(NetworkState) then) =
      _$NetworkStateCopyWithImpl<$Res>;
  $Res call({dynamic baseURL, dynamic latitude, dynamic longitude});
}

/// @nodoc
class _$NetworkStateCopyWithImpl<$Res> implements $NetworkStateCopyWith<$Res> {
  _$NetworkStateCopyWithImpl(this._value, this._then);

  final NetworkState _value;
  // ignore: unused_field
  final $Res Function(NetworkState) _then;

  @override
  $Res call({
    Object? baseURL = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
  }) {
    return _then(_value.copyWith(
      baseURL: baseURL == freezed
          ? _value.baseURL
          : baseURL // ignore: cast_nullable_to_non_nullable
              as dynamic,
      latitude: latitude == freezed
          ? _value.latitude
          : latitude // ignore: cast_nullable_to_non_nullable
              as dynamic,
      longitude: longitude == freezed
          ? _value.longitude
          : longitude // ignore: cast_nullable_to_non_nullable
              as dynamic,
    ));
  }
}

/// @nodoc
abstract class _$NetworkStateCopyWith<$Res>
    implements $NetworkStateCopyWith<$Res> {
  factory _$NetworkStateCopyWith(
          _NetworkState value, $Res Function(_NetworkState) then) =
      __$NetworkStateCopyWithImpl<$Res>;
  @override
  $Res call({dynamic baseURL, dynamic latitude, dynamic longitude});
}

/// @nodoc
class __$NetworkStateCopyWithImpl<$Res> extends _$NetworkStateCopyWithImpl<$Res>
    implements _$NetworkStateCopyWith<$Res> {
  __$NetworkStateCopyWithImpl(
      _NetworkState _value, $Res Function(_NetworkState) _then)
      : super(_value, (v) => _then(v as _NetworkState));

  @override
  _NetworkState get _value => super._value as _NetworkState;

  @override
  $Res call({
    Object? baseURL = freezed,
    Object? latitude = freezed,
    Object? longitude = freezed,
  }) {
    return _then(_NetworkState(
      baseURL: baseURL == freezed ? _value.baseURL : baseURL,
      latitude: latitude == freezed ? _value.latitude : latitude,
      longitude: longitude == freezed ? _value.longitude : longitude,
    ));
  }
}

/// @nodoc

class _$_NetworkState extends _NetworkState with DiagnosticableTreeMixin {
  const _$_NetworkState(
      {this.baseURL = '', this.latitude = -999, this.longitude = -999})
      : super._();

  @JsonKey()
  @override
  final dynamic baseURL;
  @JsonKey()
  @override
  final dynamic latitude;
  @JsonKey()
  @override
  final dynamic longitude;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'NetworkState(baseURL: $baseURL, latitude: $latitude, longitude: $longitude)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'NetworkState'))
      ..add(DiagnosticsProperty('baseURL', baseURL))
      ..add(DiagnosticsProperty('latitude', latitude))
      ..add(DiagnosticsProperty('longitude', longitude));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _NetworkState &&
            const DeepCollectionEquality().equals(other.baseURL, baseURL) &&
            const DeepCollectionEquality().equals(other.latitude, latitude) &&
            const DeepCollectionEquality().equals(other.longitude, longitude));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(baseURL),
      const DeepCollectionEquality().hash(latitude),
      const DeepCollectionEquality().hash(longitude));

  @JsonKey(ignore: true)
  @override
  _$NetworkStateCopyWith<_NetworkState> get copyWith =>
      __$NetworkStateCopyWithImpl<_NetworkState>(this, _$identity);
}

abstract class _NetworkState extends NetworkState {
  const factory _NetworkState(
      {dynamic baseURL, dynamic latitude, dynamic longitude}) = _$_NetworkState;
  const _NetworkState._() : super._();

  @override
  dynamic get baseURL;
  @override
  dynamic get latitude;
  @override
  dynamic get longitude;
  @override
  @JsonKey(ignore: true)
  _$NetworkStateCopyWith<_NetworkState> get copyWith =>
      throw _privateConstructorUsedError;
}
