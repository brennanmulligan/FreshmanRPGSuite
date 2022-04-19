import 'package:freezed_annotation/freezed_annotation.dart';
import '../failure/failure.dart';
part 'result.freezed.dart';

///
/// Wrapper for `Future` responses. Used to prevent crashes caused
/// by runtime exceptions. Result can either be an instance of ResultData
/// or ResultFailure. If an exception is thrown, then `Result` is an
/// instance of `ResultFailure`. If data is received, then `Result` is an
/// instance of `ResultData`.
///
@freezed
class Result<T> with _$Result<T> {
  ///
  /// Creates an instance of `ResultData` from data `T`.
  ///
  factory Result.data({
    required T data,
  }) = ResultData;

  ///
  /// Creates and instance of `ResultFailure` from failure `Failure`.
  ///
  factory Result.failure({
    required Failure failure,
  }) = ResultFailure;
}
