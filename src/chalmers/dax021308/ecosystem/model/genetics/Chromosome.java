package chalmers.dax021308.ecosystem.model.genetics;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Chromosome implements IChromosome<GeneralGeneTypes, IGene> {

	protected Map<GeneralGeneTypes, IGene> chromosomeMap;

	public Chromosome() {
		this.chromosomeMap = makeNewMap(null);
	}

	private Chromosome(Map<GeneralGeneTypes,IGene> chromosome) {
		this.chromosomeMap = makeNewMap(chromosome);
	}

	/*private Map<GeneralGeneTypes, IGene> makeNewMap(){
		return new EnumMap<GeneralGeneTypes, IGene>(GeneralGeneTypes.class);
	}*/

	private Map<GeneralGeneTypes, IGene> makeNewMap(Map<GeneralGeneTypes,IGene> chromosome){

		Map<GeneralGeneTypes,IGene> newChromosome = new EnumMap<GeneralGeneTypes, IGene>(GeneralGeneTypes.class);
		if (chromosome != null) {
			Iterator<Entry<GeneralGeneTypes,IGene>> it = chromosome.entrySet().iterator();
			while(it.hasNext()) {
				Entry<GeneralGeneTypes,IGene> entry = it.next();
				newChromosome.put(entry.getKey(), entry.getValue().getCopy());
			}
		}
		return newChromosome;
	}

	@Override
	public IChromosome<GeneralGeneTypes, IGene> getCopy(){
		return new Chromosome(this.chromosomeMap);
	}

	@Override
	public int getNumberOfGenes() {
		return this.chromosomeMap.size();
	}


	@Override
	public void addGene(GeneralGeneTypes geneType, IGene gene) {
		this.chromosomeMap.put(geneType, gene);

	}

	@Override
	public Map<GeneralGeneTypes,IGene> getGenes() {
		return this.chromosomeMap;
		//return makeNewMap(this.chromosomeMap);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGeneNames()
	 */
	@Override
	public List<GeneralGeneTypes> getGeneNames() {
		return new ArrayList<GeneralGeneTypes>(this.chromosomeMap.keySet());
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGeneCurrentValue(java.lang.Object)
	 */
	@Override
	public double getGeneCurrentDoubleValue(GeneralGeneTypes geneType) {
		if (chromosomeMap.containsKey(geneType))
			return chromosomeMap.get(geneType).getCurrentDoubleValue();
		else
			throw new IllegalArgumentException(geneType.name() + " is not part of this" +
					" chromosome.");
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#setCurrentValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setCurrentDoubleValue(GeneralGeneTypes geneType, double currentValue) {
		if (chromosomeMap.containsKey(geneType))
			chromosomeMap.get(geneType).setCurrentDoubleValue(currentValue);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#mutateChromosome()
	 */
	@Override
	public void mutateChromosome() {
		for(IGene g : this.chromosomeMap.values()){
			g.mutate();
		}

	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGene(java.lang.Object)
	 */
	@Override
	public IGene getGene(GeneralGeneTypes geneType) {
		if (chromosomeMap.containsKey(geneType))
			return (IGene) this.chromosomeMap.get(geneType);
		else
			throw new IllegalArgumentException(geneType.name() + " is not part of this" +
					" chromosome.");

	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IChromosome#isGeneActive(java.lang.Object)
	 */
	@Override
	public boolean isGeneActive(GeneralGeneTypes geneType) {
		return this.chromosomeMap.get(geneType).isGeneActive();
	}

}
