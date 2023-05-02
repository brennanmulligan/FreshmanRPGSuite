import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

class VanityDTO extends Equatable {
  final int vanityID;
  final String name;
  final String description;
  final String textureName;
  final String vanityType;
  final int price;

  const VanityDTO(
      {required this.vanityID,
      required this.name,
      required this.description,
      required this.textureName,
      required this.vanityType,
      required this.price});

  @override
  List<Object?> get props =>
      [vanityID, name, description, textureName, vanityType, price];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'vanityID': vanityID,
      'name': name,
      'description': description,
      'textureName': textureName,
      'vanityType': vanityType,
      'price': price
    };
  }

  factory VanityDTO.fromJson({
    required JSON json,
  }) {
    return VanityDTO(
        vanityID: json['vanityID'],
        name: json['name'],
        description: json['description'],
        textureName: json['textureName'],
        vanityType: json['vanityType'],
        price: json['price']);
  }
}
