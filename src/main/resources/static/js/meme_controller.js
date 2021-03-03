'use strict'
let MemeController = function () {
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    const positiveBtnSetClass = 'btn-success';
    const positiveBtnNSetClass = 'btn-outline-success';
    const negativeBtnSetClass = 'btn-danger';
    const negativeBtnNSetClass = 'btn-outline-danger';

    const SCORE_STATE = {
        POSITIVE: "POSITIVE",
        NEGATIVE: "NEGATIVE",
        NONE: "NONE",
    }

    class MemeController {

        constructor(memeId, positiveButton, negativeButton, scoreState = SCORE_STATE.NONE) {
            this.memeId = memeId;
            this.positiveButton = positiveButton;
            this.negativeButton = negativeButton;
            this._scoreState = scoreState;

            positiveButton.addEventListener('click', () => {
                let newScoreState = null;
                switch (this.scoreState) {
                    case SCORE_STATE.POSITIVE:
                        newScoreState = SCORE_STATE.NONE
                        break;
                    case SCORE_STATE.NEGATIVE:
                        newScoreState = SCORE_STATE.POSITIVE
                        break;
                    case SCORE_STATE.NONE:
                        newScoreState = SCORE_STATE.POSITIVE
                        break;
                }
                if (newScoreState) {
                    this.scoreButtonClick(newScoreState);
                }
            })

            negativeButton.addEventListener('click', () => {
                let newScoreState = null;
                switch (this.scoreState) {
                    case SCORE_STATE.POSITIVE:
                        newScoreState = SCORE_STATE.NEGATIVE;
                        break;
                    case SCORE_STATE.NEGATIVE:
                        newScoreState = SCORE_STATE.NONE;
                        break;
                    case SCORE_STATE.NONE:
                        newScoreState = SCORE_STATE.NEGATIVE;
                        break;
                }
                if (newScoreState) {
                    this.scoreButtonClick(newScoreState);
                }
            })
        }

        get scoreState() {
            return this._scoreState
        }

        set scoreState(scoreState) {
            this._scoreState = scoreState;
            this.updateScoreStateButtons();
        }

        updateNegativeScoreSum(negativeScoreSum) {
            this.negativeButton.textContent = negativeScoreSum;
        }

        updatePositiveScoreSum(positiveScoreSum) {
            this.positiveButton.textContent = positiveScoreSum;
        }

        updateScoreStateButtons() {
            switch (this.scoreState) {
                case SCORE_STATE.POSITIVE:
                    this.positiveButton.classList.remove(positiveBtnNSetClass);
                    this.negativeButton.classList.remove(negativeBtnSetClass);
                    this.positiveButton.classList.add(positiveBtnSetClass);
                    this.negativeButton.classList.add(negativeBtnNSetClass);
                    break;
                case SCORE_STATE.NEGATIVE:
                    this.positiveButton.classList.remove(positiveBtnSetClass);
                    this.negativeButton.classList.remove(negativeBtnNSetClass);
                    this.positiveButton.classList.add(positiveBtnNSetClass);
                    this.negativeButton.classList.add(negativeBtnSetClass);
                    break;
                case SCORE_STATE.NONE:
                    this.positiveButton.classList.remove(positiveBtnSetClass);
                    this.negativeButton.classList.remove(negativeBtnSetClass);
                    this.positiveButton.classList.add(positiveBtnNSetClass);
                    this.negativeButton.classList.add(negativeBtnNSetClass);
                    break;
            }
        }

        async scoreButtonClick(newScoreState) {
            let response = await fetch('/meme/' + this.memeId + '/score', {
                method: 'POST',
                cache: 'no-cache',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify({memeId: this.memeId, scoreState: newScoreState})
            })
            if (response.ok) {
                await this.refreshMemeScoreState()
            }
        }

        async refreshMemeScoreState() {
            let response = await fetch('/meme/' + this.memeId, {
                method: 'GET',
                cache: 'no-cache',
                headers: {
                    'Accept': 'application/json',
                    [csrfHeader]: csrfToken
                }
            })
            if (response.ok) {
                let memeSummary = await response.json();
                console.log(memeSummary);
                this.scoreState = String(memeSummary['scoreState']);
                this.updatePositiveScoreSum(Number(memeSummary['positiveScoreSum']));
                this.updateNegativeScoreSum(Number(memeSummary['negativeScoreSum']));
            }
        }
    }
    return MemeController;
}()
