///
/// Types of resposnes we get from the server when trying to create player.
///
enum PlayerResponseType {
  created,
  alreadyExists,
  crewNotValid,
  majorNotValid,
  sectionNotValid,
  networkFailure,
}

extension PlayerResponseTypeExtension on PlayerResponseType {
  String description() {
    switch (this) {
      case PlayerResponseType.created:
        return "Player created!";
        case PlayerResponseType.alreadyExists:
        return "Player already created!";
        case PlayerResponseType.crewNotValid:
        return "Invalid crew...";
        case PlayerResponseType.majorNotValid:
        return "Invalid major...";
        case PlayerResponseType.sectionNotValid:
        return "Invalid section...";
        case PlayerResponseType.networkFailure:
        return "Network Failure...";
    }
  }
}