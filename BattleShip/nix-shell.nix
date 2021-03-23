# https://discourse.nixos.org/t/how-to-create-a-development-environment-with-intellij-idea-and-openjdk/10153
# Read if jdk not working in intellij

with (import (import ./nix/sources.nix).nixpkgs) { config.allowUnfree = true; };
stdenv.mkDerivation {
  name = "idea";
  # nativeBuildInputs = [ cmake gcc  ];
  buildInputs = [ 
    jdk 
    jetbrains.idea-ultimate 
    pandoc texlive.combined.scheme-full
  ];
}

# For pandoc, please uncomment line 11
# It is a huge package (latex), so it won't be added by deault