#!/bin/bash

# Répertoire de votre dépôt Git
REPO_DIR="/chemin/vers/votre/repo"

# Définir la branche par défaut (modifiable si nécessaire)
BRANCH_NAME="main"

# Naviguer vers le répertoire du dépôt
cd "$REPO_DIR" || exit

# Boucle infinie
while true; do
    # Vérifier les modifications dans le répertoire de travail
    if [[ $(git status --porcelain) ]]; then
        # Si des modifications sont détectées, ajouter et pousser
        git add .
        git commit -m "Mise à jour automatique : $(date)"
        git push origin "$BRANCH_NAME"
        echo "Changements détectés et poussés à $(date)"
    fi

    # Attendre quelques secondes avant de vérifier à nouveau
    sleep 5  # Ajustez ce délai selon vos besoins
done
