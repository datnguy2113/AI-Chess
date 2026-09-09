import { useContext, useEffect, useState } from "react";
import AgentOptions from "../components/AgentOptions";
import Button from "../components/Button";
import OptionDescription from "../components/OptionDescriptions";
import Title from "../components/Title";
import MouseEventContext from "../contexts/MouseEventContext";
import type { Player } from "../../public/static/options";
import PlayerBackGround from "../components/PlayerBackground";
import ChosenPlayerContext from "../contexts/ChosenPlayerContext";
import TransitionContext from "../contexts/TransitionContext";


const LandingPage = () => { 
    const [hover1, setHover1] = useState<Player | null>(null);
    const [hover2, setHover2] = useState<Player | null>(null);
    const [firstPlayer, setFirstPlayer] = useState<Player | null>(null);
    const [secondPlayer, setSecondPlayer] = useState<Player | null>(null);
    const transitionContext = useContext(TransitionContext);

    const handleHover1 = (name: Player) => {
        setHover1(name);
    };

    const handleHover2 = (name: Player) => {
        setHover2(name);
    };

    const handleMouseOut1 = () => {
        setHover1(null);
    };

    const handleMouseOut2 = () => {
        setHover2(null);
    };

    const handleClick1 = (name: Player) =>  {
        setFirstPlayer(name);
    };

    const handleClick2 = (name: Player) => {
        setSecondPlayer(name);
    };

    useEffect(() => {
        if (transitionContext) {
            if (transitionContext.isShow) {
                transitionContext.handleTransition(false);
            }
        }
    }, []);

    return (
        <MouseEventContext
            value={
                {
                    hoverHandler1: handleHover1,
                    hoverHandler2: handleHover2,
                    mouseOutHandler1: handleMouseOut1,
                    mouseOutHandler2: handleMouseOut2,
                    clickHandler1: handleClick1,
                    clickHandler2: handleClick2
                }
            }
        >
             <ChosenPlayerContext
                value={
                    {
                        firstChosenPlayer: firstPlayer, 
                        secondChosenPlayer: secondPlayer
                    }
                }
            >
                <div className="max-w-260 min-h-screen mx-auto flex flex-col items-center justify-evenly overflow-hidden">
                    <Title/>
                    <div className="w-full flex">
                        <OptionDescription
                            order="first"
                            hover={hover1}
                        />
                        <div className="w-full flex flex-col items-center gap-7">
                            <div className="flex flex-col sm:flex-row items-center w-full justify-evenly gap-4">
                                <AgentOptions
                                    names={["ChatGPT", "Random", "Human", "Alpha-Beta"]}
                                    id={1}
                                />
                                <p className="font-black text-3xl">VS</p>
                                <AgentOptions
                                    names={["ChatGPT", "Random", "Human", "Alpha-Beta"]}
                                    id={2}
                                />
                            </div>
                            <div className="self-center">
                                <Button
                                    button={
                                        {
                                            onClick: () => {
                                                if (transitionContext) {
                                                    transitionContext.handleTransition(true);
                                                    transitionContext.toPage(`/game/${firstPlayer}/${secondPlayer}`);
                                                }
                                            },
                                            disabled: (firstPlayer === null) || (secondPlayer === null)
                                        }
                                    }
                                >
                                    Start
                                </Button>
                            </div>
                        </div>
                        
                        <OptionDescription
                            order="second"
                            hover={hover2}
                        />
                    </div>
                    <PlayerBackGround
                        isFirstPlayer={true}
                        chosenPlayer={firstPlayer}
                    />
                    <PlayerBackGround
                        isFirstPlayer={false}
                        chosenPlayer={secondPlayer}
                    />
                </div>
            </ChosenPlayerContext>
        </MouseEventContext>
    );
};

export default LandingPage;