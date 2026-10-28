# GitHub push and authentication — do this yourself

The initial local commit exists (`e2ca9c2`), but the HTTPS push failed authentication. GitHub does not accept your normal account password for Git over HTTPS.

## Try GitHub CLI first
1. In Android Studio, open **View → Tool Windows → Terminal**.
2. Type only this command and press Return:
   `gh --version`
3. If it prints a version, run:
   `gh auth login`
4. Choose **GitHub.com**, then **HTTPS**, then answer **Y** when asked whether to authenticate Git with your GitHub credentials. Choose **Login with a web browser** if offered. Follow the browser prompt yourself; never send your password, token, or one-time code to anyone.
5. Back in the terminal, run:
   `git push -u origin main`
6. Open your repository URL in a normal browser and confirm that files and the README are visible. Copy that exact URL into the report.

## If `gh` says command not found
Do not paste shell output back into Terminal. Check whether Homebrew is installed by running:
`brew --version`
If Homebrew is available, Git Credential Manager can be installed with:
`brew install --cask git-credential-manager`
Then retry `git push -u origin main` and complete the browser authentication flow if it appears. If your computer says Homebrew is unavailable, stop and ask for the next step rather than running random commands.

## Commit history integrity
Only commit real changes you make from now on. A later series of truthful commits can document actual fixes, tests, evidence, and documentation, but it cannot honestly recreate development history that never occurred. Do not invent dates or claim that unrun tests passed.
